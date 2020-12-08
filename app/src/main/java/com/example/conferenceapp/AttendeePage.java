package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import src.Attendee;
import src.Event;
import src.EventManager;
import src.UserAccount;

import com.example.conferenceapp.MainActivity;

import java.util.Map;

public class AttendeePage extends AppCompatActivity {
    private FloatingActionButton fabMsg;
    private FloatingActionButton fabEvent;
    private RecyclerView rv;

    Event items[] = new Event[] {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_page);

        fabMsg = findViewById(R.id.fabMsg);
        fabEvent = findViewById(R.id.fabAddEvent);
        rv = findViewById(R.id.recycleView);
        Global global = (Global) getApplicationContext();


        int i = 0;
        for (Map.Entry<String,Event> entry : EventManager.getEventList().entrySet()){

            items[i] = entry.getValue();
            i++;
        }


        EventAdapter myAdapter = new EventAdapter(this, items, global);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

        fabEvent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendeePage.this, EventSignUpPage.class);
                startActivity(intent);
            }
        });

        fabMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendeePage.this, MessagePage.class);
                startActivity(intent);
            }
        });

    }
}