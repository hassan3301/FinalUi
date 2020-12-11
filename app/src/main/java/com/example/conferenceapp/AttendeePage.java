package com.example.conferenceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import src.Attendee;
import src.Event;
import src.EventManager;
import src.User;
import src.UserAccount;

import com.example.conferenceapp.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AttendeePage extends AppCompatActivity {
    private FloatingActionButton fabMsg;
    private FloatingActionButton fabEvent;
    private RecyclerView rv;
    private Button logout;
    private Boolean VIP;
    private String un;

    ArrayList<Event> items = new ArrayList<Event>() {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendee_page);

        fabMsg = findViewById(R.id.fabMsg);
        fabEvent = findViewById(R.id.fabAddEvent);
        rv = findViewById(R.id.recycleView);
        logout = findViewById(R.id.button);
        Global global = (Global) getApplicationContext();

        VIP = getIntent().getBooleanExtra("VIP", false);
        un = getIntent().getStringExtra("user_name");

        if (VIP){
            items = global.getTc().getVac().viewVIPEvents();
        }
        else {
            items = global.getTc().getAc().getScheduledEvents(un);
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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    global.getTc().logout();
                }
                catch (IOException io){
                    System.out.println("x");
                }
                catch(ClassNotFoundException cnf){
                    System.out.println("x");
                }

                Intent intent = new Intent(AttendeePage.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}