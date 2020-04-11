package com.example.worktime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class WorkHoursActivity extends AppCompatActivity {

    String rfid, date_in, date_out;
    TimePicker time_out_picker;
    DatePicker date_out_picker;
    Button accept_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //na początku jest ładowany layout wyboru godziny początkowej
        //potem po wciśnięciu przycisku dalej, załadowany zostanie nowy layout w którym będzi emożnaa wybrac godzinę końcową
        setContentView(R.layout.activity_timestamp);

        Intent intent = getIntent();
        rfid = intent.getStringExtra("rfid");
        final Button next_button = findViewById(R.id.next_button);
        //tutaj wybieramy date wejścia
        final DatePicker date_in_picker = findViewById(R.id.date_in_picker);
        //a tu godzina wejścia
        final TimePicker time_in_picker = findViewById(R.id.time_in_picker);

        //ustawiamy żeby pokazywło widżet wyboru godziny w trybie 24 h a nie 12 am/pm
        time_in_picker.setIs24HourView(true);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sklejam stringa daty wejścia w postaci YYYY-MM-DD HH:MM
                date_in = date_in_picker.getYear() + "-" + date_in_picker.getMonth() + "-" + date_in_picker.getDayOfMonth();
                date_in += " " +  time_in_picker.getCurrentHour() + ":" + time_in_picker.getCurrentMinute();
                //tutaj zmieniamy layout i wyszukujemy nowe obiekty żeby móc wyciągać z nich date i godzine i przechwytywać wciśnięcie przycisku wyślij

                setContentView(R.layout.activity_time_out);
                //a tu są wyjścia
                date_out_picker = findViewById(R.id.date_out_picker);
                time_out_picker = findViewById(R.id.time_out_picker);
                accept_button = findViewById(R.id.send_date_button);
                time_out_picker.setIs24HourView(true);

                //to na pewno możnaby zrobić lepiej, żeby nie był actionlistener w innym actionlistener, ale nie wykombinowałem na szybko nic lepszego
                //to i tak tylko inżynierka, więc looz
                accept_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //sklejam stringa daty wyjścia w postaci YYYY-MM-DD HH:MM
                        date_out = date_out_picker.getYear() + "-" + date_out_picker.getMonth() + "-" + date_out_picker.getDayOfMonth();
                        date_out += " " +  time_out_picker.getCurrentHour() + ":" + time_out_picker.getCurrentMinute();
                        new WorkHoursTask(getApplicationContext()).execute(rfid, date_in, date_out);
                        finish();
                    }
                });
            }
        });


    }
}
