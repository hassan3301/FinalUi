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

import src.Event;
import src.EventManager;
import src.OrganizerController;
import src.Room;
import src.SpeakerController;

public class SchedulerFragment extends Fragment {

    private RecyclerView recyclerView;
    private EventCardAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<EventCard> eventCardArrayList;
    private FloatingActionButton fabAddevent;
    Global global;

    public SchedulerFragment(Global global){
        this.global = global;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Scheduler");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_scheduler, container, false);

        ArrayList<Event> eventArrayList = new ArrayList<>(global.getTc().getOC().viewAllEventsArrayList());
        eventCardArrayList = EventsListFragment.getEventCardList(eventArrayList);

        fabAddevent = root.findViewById(R.id.fabSchedulerAddEvent);
        fabAddevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });



        //Testing

        //Testing



        recyclerView = root.findViewById(R.id.event_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new EventCardAdapter(eventCardArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    public void openDialog() {
        EventAddDialog ead = new EventAddDialog(global, new EventAddDialog.EventDialogCallback() {
            @Override
            public void onDialogCallback() {
                System.out.println("CALLBACK");
                adapter.updateData(EventsListFragment.getEventCardList( global.getTc().getOC().viewAllEventsArrayList()));
            }
        });
        ead.show(getChildFragmentManager(), "eventadder dialog");
    }


}
