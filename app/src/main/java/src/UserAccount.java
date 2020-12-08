package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserAccount implements Serializable {
    static Map<String, Message> idToMessage = new HashMap<>();
    static Map<String, Attendee> unToAttendee = new HashMap<>();
    static Map<String, Speaker> unToSpeaker = new HashMap<>();
    static Map<String, Organizer> unToOrganizer = new HashMap<>();
    static Map<String, VIPAttendee> unToVip = new HashMap<>();
    public EventManager eventManager;
    TimeClass timeClass = new TimeClass();

    public UserAccount(){
        this.eventManager = new EventManager();
    }

    public static Map<String, Message> getIdToMessage() {
        return idToMessage;
    }

    public static Map<String, Attendee> getUnToAttendee() {
        return unToAttendee;
    }

    public static Map<String, Speaker> getUnToSpeaker() {
        return unToSpeaker;
    }

    public static Map<String, Organizer> getUnToOrganizer(){
        return unToOrganizer;
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
                boolean val = timeClass.compareTime(event_object.getStart_time(), event_object.getEnd_time(), event);
                if (!val){
                    return false;
                }
            }
        }
        if (user.getClass().getName().equals("Speaker")){
            for (String event : ((Speaker) user).getSpeakingAt()) {
                boolean val = timeClass.compareTime(event_object.getStart_time(), event_object.getEnd_time(), event);
                if (!val){
                    return false;
                }
            }
        }
        return true;
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

    /**
     * Sets the message with id id to archived/unarchived
     * @param id the id of the message you want to set
     * @param archived whether you want to set the message to archived or unarchived
     */
    public void archiveMessage(String id, boolean archived) {
        idToMessage.get(id).setArchived(archived);
    }

    /**
     * Sets the message with id id to read/unread
     * @param id the id of the message you want to set
     * @param read whether you want the message to be read or unread
     */
    public void markRead(String id, boolean read) {
        idToMessage.get(id).setRead(read);
    }


    public void printUser(String type) {
        if(type.equalsIgnoreCase("Organizer")) {
            for(String s:unToOrganizer.keySet()) {
                System.out.println(s);
            }
        }
        else if(type.equalsIgnoreCase("Attendee")) {
            for(String s:unToAttendee.keySet()) {
                System.out.println(s);
            }
        }
        else if(type.equalsIgnoreCase("VIP Attendee")) {
            for(String s:unToVip.keySet()) {
                System.out.println(s);
            }
        }
        else {
            for(String s:unToSpeaker.keySet()) {
                System.out.println(s);
            }
        }
    }

    public void deleteMessage(String id, String receiver) {
        String sender = getUser(receiver).removeMessageReceived(id);
        getUser(sender).removeMessageSent(id);
    }

    public Map<String, ArrayList<String>> viewAllMessages(String username) {
        return getUser(username).getAllMessagesReceived();
    }

    /**
     * Shows the user a list of messages from another user
     * @param u1 the username of the speaker viewing the messages
     * @param u2 the username of the user that sent the messages
     * @return a list of messages
     */
    public ArrayList<String> viewMessages(String u1, String u2) {
        ArrayList<String> messages = getUser(u1).getMessages_received(u2);
        if(messages != null) {
            for (String id: messages) {
                idToMessage.get(id).setRead(true);
            }
        }
        return messages;
    }
}