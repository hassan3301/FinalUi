package com.example.conferenceapp;

import src.Event;

public class EventCard {
    private String title;
    private String description;
    private String[] speakername;
    private String time;
    private String roomlocation;

    public EventCard(Event e){
        title = e.getName();
        description = e.getDescription();
        speakername = e.getSpeaker();
        time = e.getStart_time().getTime().toString();
        roomlocation = e.getPlace();
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String[] getSpeakername(){
        return speakername;
    }

    public String getTime(){
        return time;
    }

    public String getRoomlocation(){
        return roomlocation;
    }
}
