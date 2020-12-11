package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Map;

import src.UserAccount;

public class SpeakerViewMessagePage extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_view_message_page);

        rv = findViewById(R.id.rvMsg);
        Global global = (Global) getApplicationContext();
        String userFrom = getIntent().getStringExtra("userFrom");

        Map<String, ArrayList<String>> allmessages = (global.getTc().getScon().getAllMessages());

        SpeakerMessageAdapter myAdapter = new SpeakerMessageAdapter(SpeakerViewMessagePage.this,
                UserAccount.getUnToAttendee().get(global.getTc().getAc().username).getMessages_received(userFrom), global, userFrom);
        rv.setAdapter(myAdapter);
        rv.setLayoutManager(new LinearLayoutManager(SpeakerViewMessagePage.this));
    }
}