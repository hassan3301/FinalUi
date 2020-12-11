package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.Map;

import src.Event;
import src.EventManager;

public class EventSignUpPage extends AppCompatActivity {

    private RecyclerView rv;
    Event items[] = new Event[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sign_up_page);
        Global global = (Global) getApplicationContext();

        int i = 0;
        for (Map.Entry<String,Event> entry : EventManager.getEventList().entrySet()){

            items[i] = entry.getValue();
            i++;
        }

        rv = findViewById(R.id.rvSignUp);

        EventSignUpAdapter myAdapter = new EventSignUpAdapter(this, items, global);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}