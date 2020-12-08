package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import src.Event;

public class EventSignUpPage extends AppCompatActivity {

    private RecyclerView rv;
    Event items[] = new Event[] {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sign_up_page);
        Global global = (Global) getApplicationContext();

        rv = findViewById(R.id.rvSignUp);

        EventSignUpAdapter myAdapter = new EventSignUpAdapter(this, items, global);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}