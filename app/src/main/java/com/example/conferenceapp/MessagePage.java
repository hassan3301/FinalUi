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
    private EditText from;
    private RecyclerView rv;
    private Button enter;

    ArrayList<String> s1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);
        Global global = (Global) getApplicationContext();

        fab = findViewById(R.id.fabSend);
        fabAdd = findViewById(R.id.fabAddMessenger);
        rv = findViewById(R.id.rvMsg);
        from = findViewById(R.id.etFrom);
        enter = findViewById(R.id.btnFromEnter);

        String userFrom = from.getText().toString();
        s1 = UserAccount.getUnToAttendee().get(global.getTc().getAc().username).getMessages_received(userFrom);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageAdapter myAdapter = new MessageAdapter(MessagePage.this, s1, global, userFrom);
                rv.setAdapter(myAdapter);
                rv.setLayoutManager(new LinearLayoutManager(MessagePage.this));
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