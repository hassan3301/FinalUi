package UseCase;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

import Entities.Event;
import Entities.User;

public class EventManager implements Serializable {

    public static Map<String, Event> EventList = new HashMap<>();

    public EventManager(){ }

    /**
     * A void method to update an event's list of attendees if the max occupancy is not reached.
     * @param event the event that is being added to.
     * @param name the name of the user joining the event.
     */
    public void update_attendees(Event event, User name){
       if (!event.is_full()) {
           event.attendee_list.add(name.getName());
       }
    }

    /**
     * creates an event.
     * @param name name of the event
     * @param place room of the event
     * @param start_time start time of the event
     * @param end_time end time of the event
     * @param Speaker speakers at the event
     * @param description description of the event
     * @param limit capacity of the event
     * @param type type of event
     * @param attendee_list attendees in event
     */
    public void makeEvent(String name, String place, Calendar start_time, Calendar end_time, ArrayList<String> Speaker, String description, int limit, String type, ArrayList<String> attendee_list){
        Event e1 = new Event(name, place,  Speaker, description, start_time, end_time, type, limit);
        e1.attendee_list.addAll(attendee_list);
        EventManager.EventList.put(name, e1);
    }

    /**
     * returns map of events.
     * @return map of events
     */
    public static Map<String, Event> getEventList() {
        return EventList;
    }

    /**
     * A boolean method that checks if the event has a time conflict with another event.
     * @param events the list of events that may be conflicting.
     * @param startTime the starting time of the event.
     * @param endTime the ending time of the event.
     */
    public boolean isConflicting(ArrayList<String> events, Calendar startTime, Calendar endTime) {
        for(String event: events) {
            if (events.size()>0 && EventList.size()>0) {
                if (EventList.get(event).getStart_time().compareTo(endTime) <= 0 &&
                        EventList.get(event).getEnd_time().compareTo(startTime) >= 0) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * A void method that creates an event.
     * @param eventName the name of the event.
     * @param roomName the name of the room the event is in.
     * @param speakerNames the name(s) of the speaker talking at the event.
     * @param description a description of the event.
     * @param startTime the starting time of the event.
     * @param endTime the ending time of the event.
     * @param type the type of the event.
     */
    public void createEvent(String eventName,  String roomName, ArrayList<String> speakerNames, String description,
                            Calendar startTime, Calendar endTime, String type, int limit){
        Event newEvent = new Event(eventName, roomName, speakerNames, description, startTime, endTime, type, limit);
        addEvent(newEvent);
    }

    /**
     * A void method that adds an event to the event list.
     * @param event the event that is being added to the list.
     */
    public void addEvent(Event event) {
        EventList.put(event.getName(), event);
    }

    /**
     * specifies whether the given event can be deleted
     * @param event_name
     * @return true if the event can be deleted, false otherwise
     */
    public boolean canDeleteEvent(String event_name){
        return EventList.containsKey(event_name);
    }




    /**
     * returns the event attendees of a specified event
     * @param eventname the key of the event
     * @return an arraylist of strings of keys of attendees
     */
    public ArrayList<String> getEventAttendees(String eventname){
        return EventList.get(eventname).getAttendee_list();
    }

    /**
     * adds speaker to the speakerlist in the event
     * @param eventname name of event
     * @param speakerun speaker to be added
     */
    public void addSpeaker(String eventname, String speakerun){

        EventList.get(eventname).setSpeaker(speakerun);
    }

    /**
     * returns the string array of speaker usernames speaking at a given event
     * @param eventname name of event
     * @return string array of speaker usernames
     */
    public ArrayList<String> getEventSpeaker(String eventname){
        if( EventList.get(eventname).getSpeaker()!=null){
            return EventList.get(eventname).getSpeaker();
        }
        return new ArrayList<String>();
    }

    /**
     * return start time of the specified event
     * @param event event name
     * @return Calendar object of start time
     */
    public Calendar getStartTime(String event){
        return EventList.get(event).getStart_time();
    }

    /**
     * return end time of the specified event.
     * @param event event name
     * @return Calendar object of end time
     */
    public Calendar getEndTime(String event){
        return EventList.get(event).getEnd_time();
    }
}