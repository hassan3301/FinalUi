package com.example.conferenceapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

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
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                ac = new AttendeeController(un.getText().toString());
                global.getTc().setAc(ac);


                try {
                    global.getTc().login(un.getText().toString(), pw.getText().toString());
                    System.out.println("y");
                }
                catch (IOException io){
                    System.out.println("x");
                }
                catch(ClassNotFoundException cnf){
                    System.out.println("x");
                }

                global.getTc().getAc().createNewAccount(un.getText().toString(), pw.getText().toString());

                try {
                    global.getTc().logout();
                }
                catch (IOException io){
                    System.out.println("x");
                }
                catch(ClassNotFoundException cnf){
                    System.out.println("x");
                }

                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}