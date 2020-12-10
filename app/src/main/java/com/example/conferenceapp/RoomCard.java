package com.example.conferenceapp;

import src.Room;

public class RoomCard {
    private String name;
    private int capacity;

    public RoomCard(Room r){
        this.name = r.getName();
        this.capacity = r.getCapacity();
    }

    public String getName(){
        return name;
    }


    public int getCapacity(){
        return capacity;
    }
}
