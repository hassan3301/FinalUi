package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class EventSignUpPage extends AppCompatActivity {

    private RecyclerView rv;
    String items[] = new String[] {"1", "2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_sign_up_page);

        rv = findViewById(R.id.rvSignUp);

        EventSignUpAdapter myAdapter = new EventSignUpAdapter(this, items);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}