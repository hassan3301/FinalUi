package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {
    private String name;
    private String place;
    private Calendar start_time;
    private Calendar end_time;
    private String[] speaker;
    private String description;
    private int limit;
    private String type;
    public ArrayList<String> attendee_list = new ArrayList<>();

    /**
     * Constructor for the event entity
     * @param name the name of the event
     * @param place the place (room) the event will take place
     * @param speaker the username of the speaker assigned to give a talk
     * @param description the description of the event
     * @param start_time the start time of the event
     * @param end_time the end time of the event
     * @param type the type of the event being created
     */
    public Event(String name, String place, String[] speaker, String description, Calendar start_time,
                 Calendar end_time, String type, int limit){
        this.name = name;
        this.place = place;
        this.speaker = speaker;
        this.start_time = start_time;
        this.limit = limit;
        this.description = description;
        this.end_time = end_time;
        this.type = type;
    }

    /**
     * Sets the event's type
     * @param type type of the event
     */
    public void setType(String type){
        this.type = type;
    }

    /**
     * Returns the type of the event
     * @return string representing the event's type
     */
    public String getType(){
        return this.type;
    }

    /**
     * Sets the event's attendee capacity
     * @param limit int representing the event's capacity
     */
    public void setLimit(int limit){
        this.limit = limit;
    }

    /**
     * Returns the maximum number of attendees allowed for an event
     * @return int representing the maximum no. of attendees allowed
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Sets the event's name
     * @param name name of the event
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the event's place (room)
     * @param place place/room event must take place it
     */
    public void setPlace(String place){
        this.place = place;
    }

    /**
     * Sets the event's speaker
     * @param speaker username of the speaker
     */
    public void setSpeaker(String[] speaker){
        this.speaker = speaker);
    }

    /**
     * Returns name of the event
     * @return event name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Return event's location
     * @return event's location
     */
    public String getPlace(){
        return this.place;
    }

    /**
     * @return the username of the assigned speaker
     */
    public String[] getSpeaker(){
        return speaker
    }

    /**
     * @return the string description of the event
     */
    public String getDescription(){
        return this.description;
    }

    /**
     * @return the event's start time
     */
    public Calendar getStart_time(){ return this.start_time; }

    /**
     * @return the event's end time
     */
    public Calendar getEnd_time(){return this.end_time;}

    /**
     * @return the attendees attending the event
     */
    public ArrayList<String> getAttendee_list() {
        return attendee_list;
    }

    /**
     * @return true iff the event still has space for more attendees
     */
    public boolean is_full(){
        return this.attendee_list.size() >= this.getLimit();
    }

    /**
     * @return the event's toString
     */
    public String toString(){
        return "Event name: " + this.getName() + "\nEvent Place: " + this.getPlace() + "\nEvent Speaker: " +
                this.getSpeaker() + "\nEvent Description: " + this.getDescription() + "\nEvent Date and Time: " +
                this.getStart_time().getTime().toString() + " to " + this.getEnd_time().getTime().toString();
    }

}