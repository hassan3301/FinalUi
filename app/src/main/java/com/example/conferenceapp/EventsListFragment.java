package com.example.conferenceapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import src.Event;

public class EventsListFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_eventslist, container, false);
        SpeakerPage activity = (SpeakerPage) getActivity();
        getActivity().setTitle("Events");
        ArrayList<EventCard> eventCardArrayList = activity.getSpeakingList();


        //Testing

        //Testing



        recyclerView = root.findViewById(R.id.event_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new EventCardAdapter(eventCardArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }
}
