package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MessageSendPage extends AppCompatActivity {

    private Button btn;
    private EditText un;
    private EditText msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send_page);

        btn = findViewById(R.id.btnSendMsg);
        un = findViewById(R.id.etNameSend);
        msg = findViewById(R.id.etMsgSend);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implement send message
                Intent intent = new Intent(MessageSendPage.this, AttendeePage.class);
                startActivity(intent);

            }
        });
    }
}