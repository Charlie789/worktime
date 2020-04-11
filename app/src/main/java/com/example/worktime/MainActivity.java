package com.example.worktime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText username_text_field, password_text_field;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_text_field = (EditText) findViewById(R.id.editText1);
        password_text_field = (EditText) findViewById(R.id.editText2);

        status = (TextView) findViewById(R.id.textView6);
    }

    //metoda, która wywołu signinactivity, wywoływana jest w activity_main.xml - android:onClick="loginPost"
    public void loginPost(View view) {
        String username = username_text_field.getText().toString();
        String password = password_text_field.getText().toString();
        new SigninActivity(this, status).execute(username, password);
    }
}
