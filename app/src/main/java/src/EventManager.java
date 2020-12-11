package src;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class EventManager implements Serializable {

    public static Map<String, Event> EventList = new HashMap<>();

    EventManager(){ }

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

    public void makeEvent(String name, String place, Calendar start_time, Calendar end_time, String[] Speaker, String description, int limit, String type, ArrayList<String> attendee_list){
        Event e1 = new Event(name, place, Speaker, description, start_time, end_time, type, limit);
        for (String s : attendee_list) {
            e1.attendee_list.add(s);
        }
    }
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
            if (EventList.get(event).getStart_time().compareTo(endTime) <= 0 &&
                    EventList.get(event).getEnd_time().compareTo(startTime) >= 0) {
                return true;
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
    public void createEvent(String eventName,  String roomName, String[] speakerNames, String description,
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

    public boolean canDeleteEvent(String event_name){
        return EventList.containsKey(event_name);
    }

}