package src;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserAccount implements Serializable {
    public static Map<String, Message> idToMessage = new HashMap<>();
    public static Map<String, Attendee> unToAttendee = new HashMap<>();
    public static Map<String, Speaker> unToSpeaker = new HashMap<>();
    public static Map<String, Organizer> unToOrganizer = new HashMap<>();
    public static Map<String, VIPAttendee> unToVip = new HashMap<>();
    public EventManager eventManager;

    public UserAccount(){
        this.eventManager = new EventManager();
    }

    /**
     * Returns the User object the username corresponds to.
     * This is a helper method for other methods in UserAccount to
     * prevent repeated code.
     * @param username the username of the user object we wish to retrieve
     * @return the User object (Attendee, Organiser, Speaker, VIP Attendee)
     * corresponding to the username.
     */
    public User getUser(String username){
        if (unToSpeaker.containsKey(username)) {
            return unToSpeaker.get(username);
        }
        else if (unToOrganizer.containsKey(username)) {
            return unToOrganizer.get(username);
        }
        else if (unToVip.containsKey(username)){
            return unToVip.get(username);
        }
        else  {
            return unToAttendee.get(username);
        }
    }
    /**
     * Returns a string containing all event names that the
     * User has signed up for
     * @param username  the username of the user who wishes to see their schedule
     * @return String containing event names the user has
     * signed up for
     */
    public String seeScheduledEvents(String username) {
        StringBuilder event_list = new StringBuilder();
        User user = getUser(username);
        if (user.getEventsAttending().size() != 0) {
            for (String event_name : user.getEventsAttending()) {
                event_list.append(event_name).append(", \n");
            }
        }
        return event_list.toString();
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

        User user = getUser(username);
        if (user.getEventsAttending().size() != 0) {
            for (String event : user.getEventsAttending()) {
                boolean val = this.compareTime(event_object.getStart_time(), event_object.getEnd_time(), event);
                if (!val){
                    return false;
                }
            }
        }
        if (user.getClass().getName().equals("Speaker")){
            for (String event : ((Speaker) user).getSpeakingAt()) {
                boolean val = this.compareTime(event_object.getStart_time(), event_object.getEnd_time(), event);
                if (!val){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Helper method: Returns true iff the event's start time and end time don't clash and there
     * is no event overlap
     * @param startTime the start time of the event we want to compare to
     * @param endTime the end time of the event we want to compare to
     * @param event the name of the event we are comparing
     * @return true iff the above conditions are met
     */
    public boolean compareTime(Calendar startTime, Calendar endTime, String event){
        return startTime.compareTo(EventManager.EventList.get(event).getStart_time()) != 0
                && endTime.compareTo(EventManager.EventList.get(event).getEnd_time()) != 0
                && (startTime.compareTo(EventManager.EventList.get(event).getStart_time()) <= 0 ||
                startTime.compareTo(EventManager.EventList.get(event).getEnd_time()) >= 0)
                && (endTime.compareTo(EventManager.EventList.get(event).getStart_time()) <= 0
                || endTime.compareTo(EventManager.EventList.get(event).getEnd_time()) >= 0);
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

    /**
     * Returns true iff the User with the given username is able to sign up for an
     * event. This happens if the User is available at the given
     * time and if the Event they wish to sign up for has
     * space
     * @param username  the username of the user who wishes to sign up for an event
     * @param event_name  the Event that the User wishes to sign up for
     * @return   true iff the above conditions are met, false otherwise
     */
    public boolean canSignUp(String username, String event_name){
        User user = getUser(username);
        return this.isAvailable(username, event_name) &&
                !EventManager.EventList.get(event_name).is_full()
                && !user.getEventsAttending().contains(event_name)
        && !(EventManager.EventList.get(event_name).getType().equals("VIP"));
    }

    /**
     * Returns true iff the User with the given username is able to delete the
     * event from the list of scheduled Events
     * @param username  the username of the User who wishes to delete the event
     * @param event_name  the Event that must be deleted
     * @return   true iff the above conditions are met, false otherwise
     */
    public boolean canDeleteEvent(String username, String event_name){
        User user = getUser(username);
        return user.getEventsAttending().contains(event_name);
    }


}