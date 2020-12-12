package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import src.AttendeesAccount;
import src.Event;
import src.EventManager;
import src.Message;
import src.UserAccount;

public class MessagePage extends AppCompatActivity {

    private FloatingActionButton fab;
    private FloatingActionButton fabAdd;
    public EditText from;
    private Button enter;

    ArrayList<String> s1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);
        Global global = (Global) getApplicationContext();

        fab = findViewById(R.id.fabSend);
        fabAdd = findViewById(R.id.fabAddMessenger);

        from = findViewById(R.id.etFrom);
        enter = findViewById(R.id.btnFromEnter);

        String userFrom = from.getText().toString();


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessagePage.this, AttendeeViewMessagePage.class);
                intent.putExtra("userFrom", userFrom);
                startActivity(intent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessagePage.this, MessageSendPage.class);
                startActivity(intent);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessagePage.this, AddMessengerPage.class);
                startActivity(intent);
            }
        });
    }
}