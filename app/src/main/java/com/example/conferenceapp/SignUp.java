package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import src.AttendeeController;
import src.UserAccount;

public class SignUp extends AppCompatActivity {

    private EditText name;
    private EditText un;
    private EditText pw;
    private Button btn;
    private AttendeeController ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Global global = (Global) getApplicationContext();

        name = findViewById(R.id.etName);
        un = findViewById(R.id.etUn);
        pw = findViewById(R.id.etPw);
        btn = findViewById(R.id.SignUpbtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac = new AttendeeController(un.getText().toString());
                global.getTc().setAc(ac);
                global.getTc().getAc().createNewAccount(un.getText().toString(), pw.getText().toString());

                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}