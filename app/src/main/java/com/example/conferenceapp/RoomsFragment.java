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
import src.Room;
import src.RoomManager;


public class RoomsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fabAddroom;
    Global global;

    public RoomsFragment(Global global){
        this.global = global;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Rooms");
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_rooms, container, false);

        ArrayList<RoomCard> roomCardArrayList = new ArrayList<>();
        roomCardArrayList = getRoomCardList(global.getTc().getOC().viewAllRooms());


        fabAddroom = root.findViewById(R.id.fabAddRoom);
        fabAddroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


        //Testing

        //Testing



        recyclerView = root.findViewById(R.id.rooms_recyclerview);
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new RoomCardAdapter(roomCardArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return root;
    }

    public void openDialog() {
        RoomAddDialog rad = new RoomAddDialog(global);
        rad.show(getChildFragmentManager(), "roomadd dialog");
    }

    public static ArrayList<RoomCard> getRoomCardList(ArrayList<Room> roomslist){
        ArrayList<RoomCard> returnlist = new ArrayList<RoomCard>();

        //TESTING
        Room room1 = new Room("room1");
        room1.setCapacity(14);
        returnlist.add(new RoomCard(room1));
        //TESTING

        for(Room r: roomslist){
            returnlist.add(new RoomCard(r));
        }
        return returnlist;
    }
}