package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddMessengerPage extends AppCompatActivity {

    private TextView tv;
    private EditText et;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_messenger_page);

        tv = findViewById(R.id.tvAddMessenger);
        et = findViewById(R.id.etAddMessenger);
        btn = findViewById(R.id.btnAddMessenger);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implement add messenger

                Intent intent = new Intent(AddMessengerPage.this, AttendeePage.class);
                startActivity(intent);
            }
        });
    }
}