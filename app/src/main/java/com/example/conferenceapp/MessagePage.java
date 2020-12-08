package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    private RecyclerView rv;

    Message s1[] = new Message[] {};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);
        Global global = (Global) getApplicationContext();

        fab = findViewById(R.id.fabSend);
        fabAdd = findViewById(R.id.fabAddMessenger);
        rv = findViewById(R.id.rvMsg);

        int i = 0;
        for (Map.Entry<String, Message> entry : UserAccount.getIdToMessage().entrySet()){
            s1[i] = entry.getValue();
            i++;
        }

        MessageAdapter myAdapter = new MessageAdapter(this, s1, global);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

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