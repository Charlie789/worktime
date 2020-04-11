package com.example.worktime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Panel extends AppCompatActivity {
    String rfid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel);
        Intent intent = getIntent();
        //metodą putextra przekazujemy zmienne do innej aktywności, a getStringExtra wyciągamy to.
        rfid = intent.getStringExtra("rfid");

        final Button logout_button = findViewById(R.id.logout_button);
        final Button reset_pwd_button = findViewById(R.id.reset_pwd_button);
        final Button work_hours_button = findViewById(R.id.timestamp_button);
        final TextView rfid_textview = findViewById(R.id.rfid_textview);
        rfid_textview.setText(rfid);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //zmiana widoku na ResetPwd po wciśnieciu przycisku resetuj hasło, gdzie można zmienić hasło
        reset_pwd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tworzę obiekt typu Intent - jest to po prostu aktywność, którą startujemy w start activity
                Intent reset_pwd_intent = new Intent(getApplicationContext(), ResetPwd.class);
                //put extra - używam, żeby przekazywać pomiędzy activity jakieś zmienne - tutaj i w nastepnej metodzie nr rfid. Pierwszy argument to nazwa pod jaką trzeba potem wyciągać zmienną
                //w getstringextra, a drugi to wartość zmiennej
                reset_pwd_intent.putExtra("rfid", rfid);
                startActivity(reset_pwd_intent);
            }
        });


        work_hours_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent workhours_intent = new Intent(getApplicationContext(), WorkHoursActivity.class);
                workhours_intent.putExtra("rfid", rfid);
                startActivity(workhours_intent);
            }
        });
    }
}
