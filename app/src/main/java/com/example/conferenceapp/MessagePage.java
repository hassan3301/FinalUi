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

import src.AttendeesAccount;
import src.UserAccount;

public class MessagePage extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView rv;

    String s1[] = {"1", "2"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_page);

        fab = findViewById(R.id.fabSend);
        rv = findViewById(R.id.rvMsg);

        MessageAdapter myAdapter = new MessageAdapter(this, s1);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessagePage.this, MessageSendPage.class);
                startActivity(intent);
            }
        });
    }
}