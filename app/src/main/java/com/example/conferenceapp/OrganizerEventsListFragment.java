package com.example.conferenceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import src.Event;
import src.OrganizerController;
import src.SpeakerController;

public class OrganizerEventsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<EventCard> attendinglist;
    private String un;
    private FloatingActionButton fab;
    Global global;

    public OrganizerEventsListFragment(Global global, String un){
        this.global = global;
        this.un = un;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_scheduler, container, false);
        getActivity().setTitle("My Events");
        fab = root.findViewById(R.id.fabSchedulerAddEvent);


        OrganizerController oc = global.getTc().getOC();

            attendinglist = EventsListFragment.getEventCardList(oc.getScheduledEvents(un));
            recyclerView = root.findViewById(R.id.event_recyclerview);
            layoutManager = new LinearLayoutManager(getActivity());
            adapter = new EventCardAdapter(attendinglist);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventSignUpPage.class);
                startActivity(intent);
            }
        });

        return root;
    }

}
