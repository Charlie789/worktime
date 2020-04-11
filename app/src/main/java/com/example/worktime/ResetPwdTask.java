
package com.example.worktime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class ResetPwdTask  extends AsyncTask<String, Void, String> {
    private String pwd, rfid;
    private Context context;

    public ResetPwdTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String... arg0) {
        try{
            rfid = (String)arg0[0];
            pwd = (String)arg0[1];

            String link="https://jakub-web.pl/PracaInzynierska/update_pwd.php";
            String data  = URLEncoder.encode("rfid", "UTF-8") + "=" +
                    URLEncoder.encode(rfid, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                    URLEncoder.encode(pwd, "UTF-8");

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

    @Override
    protected void onPostExecute(String result){
        if (result.equals("1")) {
            Intent panel_intent = new Intent(context, Panel.class);
            panel_intent.putExtra("rfid", rfid);
            context.startActivity(panel_intent);
        }
    }
}
