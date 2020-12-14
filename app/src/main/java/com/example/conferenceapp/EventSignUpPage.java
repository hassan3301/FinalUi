package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import src.Event;
import src.EventManager;

public class EventSignUpPage extends AppCompatActivity {

    private RecyclerView rv;
    ArrayList<Event> items = new ArrayList<Event>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sign_up_page);
        Global global = (Global) getApplicationContext();

        items.addAll(global.getTc().getEm().EventList.values());

        Calendar calendar = Calendar.getInstance();
        ArrayList<String> speakers = new ArrayList<String>();
        speakers.add("s1");
        Event e;
        e = new Event("e2", "r1", speakers, "description", calendar, calendar, "talk", 10);
        items.add(e);

        rv = findViewById(R.id.rvSignUp);

        EventSignUpAdapter myAdapter = new EventSignUpAdapter(this, items, global);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}