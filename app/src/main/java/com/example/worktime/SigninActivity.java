package com.example.worktime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

//klasa musi dziedziczyć po asyncTask, bo zapytania do bazy muszą być asynchroniczne, żeby apka się nie zamrażała na czas odpytania do bazy
//ogólnie polecam doczytać o asynchroniczności, bo może będa zainteresowani na obronie? nie wiem, ostrzegam tylko
//parametry w <> musiałem pozmieniać, bo w poradnikach, dokumentacji itd było inaczej i nie działało, dopiero na stackoverflow było jak to ogarnąć
public class SigninActivity  extends AsyncTask<String, Void, String>{
    private TextView statusField;
    private Context context;
    String username;

    //konstruktor, do którego przekazywany jest context i pole status field, dziieki czemu w przypadku nieudanego logowania, można zmienić teskst na 'nieudane logowanie' w panelu logowania
    public SigninActivity(Context context,TextView statusField) {
        this.context = context;
        this.statusField = statusField;
    }

    protected void onPreExecute(){
    }


    //najważniejsza metoda związana z asynchronciznośćią, ona działa w tle i wysyła zapytania post do serwera.
    @Override
    protected String doInBackground(String... arg0) {
        try{
            //te argumenty są zbierane z metody execute wywołanej w mainactivity
            username = (String)arg0[0];
            String password = (String)arg0[1];

            String link="https://jakub-web.pl/PracaInzynierska/android_login.php";
            String data  = URLEncoder.encode("login", "UTF-8") + "=" +
                    URLEncoder.encode(username, "UTF-8");
            data += "&" + URLEncoder.encode("haslo", "UTF-8") + "=" +
                    URLEncoder.encode(password, "UTF-8");

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    //ustawiłem w android_login.php, że jak jest błąd przy logowaniu to odpowiada zerem, a jak jest ok to odsyła nr rfid
    //dlatego jeśli jest 0 to ustawia tekst na nieudane log. a w innym wypadku przechodzi do panel_activity i przekazuje to niego nr rfid
    //bo potem na podstawie rfid zmieniane jest hasło i dodawane godziny do bazy danych
    @Override
    protected void onPostExecute(String result){
        if (result.equals("0")) {
            this.statusField.setText("Nieudane logowanie!");
        } else {
            Intent panel_intent = new Intent(context, Panel.class);
            panel_intent.putExtra("rfid", result);
            context.startActivity(panel_intent);
        }
    }
}
