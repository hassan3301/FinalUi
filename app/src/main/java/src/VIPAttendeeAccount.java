package src;

import java.util.ArrayList;
import java.util.Map;

public class VIPAttendeeAccount extends AttendeesAccount{

    public VIPAttendeeAccount(){}

    /**
     * Returns true iff the VIP attendee can sign up for the event
     * @param username  the username of the VIP attendee who wishes to sign up for an event
     * @param event_name  the Event that the VIP attendee wishes to sign up for
     * @return
     */
    @Override
    public boolean canSignUp(String username, String event_name){
        User user = getUser(username);
        return this.isAvailable(username, event_name) &&
                !EventManager.EventList.get(event_name).is_full()
                && !user.getEventsAttending().contains(event_name);
    }

    /**
     * Creates a new VIP attendee and adds it to the username to user map
     * @param username the username of the VIP attendee we want to create
     * @param password the password of the VIP attendee's account
     */
    public void addVIPAttendee(String username, String password){
        VIPAttendee vip = new VIPAttendee(username, password);
        unToVip.put(username, vip);
    }
}