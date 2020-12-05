package src;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserAccount implements Serializable {
    public static Map<String, Message> idToMessage = new HashMap<>();
    public static Map<String, Attendee> unToAttendee = new HashMap<>();
    public static Map<String, Speaker> unToSpeaker = new HashMap<>();
    public static Map<String, Organizer> unToOrganizer = new HashMap<>();
    public EventManager eventManager;

    public UserAccount(){
        this.eventManager = new EventManager();
    }

    /**
     * Returns a string containing all event names that the
     * User has signed up for
     * @param username  the username of the user who wishes to see their schedule
     * @return String containing event names the user has
     * signed up for
     */
    public String seeScheduledEvents(String username){
        String event_list = "";
        if (unToAttendee.containsKey(username)){
            Attendee user = unToAttendee.get(username);
            for (String event_name: user.getEvents()){
                event_list = event_list + event_name + ", \n";
            }
        }
        else if (unToOrganizer.containsKey(username)){
            Organizer user = unToOrganizer.get(username);
            for (String event_name: user.getEvents()){
                event_list = event_list + event_name + ", \n";
            }
        }
        else if (unToSpeaker.containsKey(username)){
            Speaker user = unToSpeaker.get(username);
            for (String event_name: user.getEvents()){
                event_list = event_list + event_name + ", \n";
            }
        }
        return event_list;
    }

    /**
     * Returns true iff the Attendee is available at a given time
     * i.e. doesn't have another event scheduled at the start time of
     * Event event_name
     * @param username  The username of the user who wishes to check if they have
     *                  another event scheduled at event_name's time
     * @param event_name The name of the event for which the time slot
     *                   availability is required
     * @return true iff the above conditions are met, false otherwise
     */
    public boolean isAvailable(String username, String event_name){
        if (!EventManager.EventList.containsKey(event_name)){
            return false;
        }
        Event event_object = EventManager.EventList.get(event_name);

        if (unToSpeaker.containsKey(username)) {
            if (unToSpeaker.get(username).getEvents().size() != 0) {
                Speaker user = unToSpeaker.get(username);
                for (String event : user.getEvents()) {
                    if (event_object.getStart_time() == EventManager.EventList.get(event).getStart_time()) {
                        return false;
                    }
                }
            }
            return true;
        }
        else if (unToOrganizer.containsKey(username)) {
            if (unToOrganizer.get(username).getEvents().size() != 0) {
                Organizer user = unToOrganizer.get(username);
                for (String event : user.getEvents()) {
                    if (event_object.getStart_time() == EventManager.EventList.get(event).getStart_time()) {
                        return false;
                    }
                }
            }
            return true;
        }
        else{
            if (unToAttendee.get(username).getEvents().size() != 0){
                Attendee user = unToAttendee.get(username);
                if (user.getEvents().size() != 0){
                    for (String event: user.getEvents()){
                        if (event_object.getStart_time() == EventManager.EventList.get(event).getStart_time()){
                            return false;
                        }
                    }
                }
            }
            return true;
        }
    }
    /**
     * Creates a message entity and store it in the map
     * Called by Attendee portal when creating a message
     * @param text The content of the message
     * @return The message object
     */
    public Message createMessage(String text) {
        Message m = new Message(text);
        idToMessage.put(m.getId(), m);
        return m;
    }
}
