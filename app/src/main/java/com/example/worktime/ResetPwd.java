package com.example.worktime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResetPwd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        Intent intent = getIntent();
        final String rfid = intent.getStringExtra("rfid");

        final Button cancel_button = findViewById(R.id.cancel_button);
        final Button accept_button = findViewById(R.id.send_date_button);
        final TextView pwd1_text = findViewById(R.id.pwd1_text);
        final TextView pwd2_text = findViewById(R.id.pwd2_text);

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd1 = pwd1_text.getText().toString();
                String pwd2 = pwd2_text.getText().toString();
                if(pwd1.equals(pwd2)) {
                    new ResetPwdTask(getApplicationContext()).execute(rfid, pwd1);
                    finish();
                }
            }
        });
    }
}
