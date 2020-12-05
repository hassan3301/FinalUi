package src;

import java.io.Serializable;
import java.util.ArrayList;

public class OrganizerAccount extends UserAccount implements MessageManager, Serializable {

    public OrganizerAccount(){}

    public void addOrganizer(String userName, String password) {
        Organizer org = new Organizer(userName, password);
        unToOrganizer.put(userName, org);
    }


    /**
     * Sends a message from an organizer to all speakers
      * @param from the username of the organizer this message is being sent from
     * @param m_id the id of the message being sent to the speaker
     */
    public void sendToSpeakers(String from, String m_id){
        for (String un: unToSpeaker.keySet() ){
            unToOrganizer.get(from).addMessageSent(un, m_id);
            unToSpeaker.get(un).addMessageReceived(from, m_id);
        }
    }

    /**
     * Sends a message from an organizer to all attendees
     * @param from the username of the organizer this message is being sent form
     * @param m_id the id of the message being sent
     */
    public void sendToAttendees(String from, String m_id){
        for (String un: unToAttendee.keySet()){
            unToOrganizer.get(from).addMessageSent(un, m_id);
            unToAttendee.get(un).addMessageReceived(from, m_id);
        }
    }
    /**
     * Returns true iff the Organiser is able to sign up for an
     * event. This happens if the Organiser is available at the given
     * time and if the Event they wish to sign up for has
     * space
     * @param username  the username of the organiser who wishes to sign up for an event
     * @param event_name  the Event that the Attendee wishes to sign up for
     * @return   true iff the above conditions are met, false otherwise
     */
    public boolean canSignUp(String username, String event_name){
        Organizer organizer = unToOrganizer.get(username);
        return this.isAvailable(username, event_name) &&
                !EventManager.EventList.get(event_name).is_full()
                && !organizer.getEvents().contains(event_name);
    }

    /**
     * Returns true iff the Organiser is able to delete the
     * event from the list of scheduled Events
     * @param username  the username of the organiser who wishes to delete the event
     * @param event_name  the Event that must be deleted
     * @return   true iff the above conditions are met, false otherwise
     */
    public boolean canDeleteEvent(String username, String event_name){
        Organizer organizer = unToOrganizer.get(username);
        return organizer.getEvents().contains(event_name);
    }

    /**
     * Sends a message to a single attendee or speaker from an organizer
     * @param from the username of the organizer sending the message
     * @param to the username of the attendee or speaker receiving the message
     * @param m_id the id of the message being sent
     */
    @Override
    public void sendTo(String from, String to, String m_id) {
        if (unToAttendee.containsKey(to)){

            unToOrganizer.get(from).addMessageSent(to, m_id);
            unToAttendee.get(to).addMessageReceived(from, m_id);

        }
        else if (unToSpeaker.containsKey(to)){

            unToOrganizer.get(from).addMessageSent(to, m_id);
            unToSpeaker.get(to).addMessageReceived(from, m_id);

        }
    }

    /**
     * Displays the messages received by an organizer from a user
     * @param u1 username of the organizer whose received messages we want to see
     * @param u2 username of the user who sent the messages to the organizer
     * @return the list of messages received by the organizer u1 from the user u2
     */
    @Override
    public ArrayList<String> viewMessages(String u1, String u2) {
        return unToOrganizer.get(u1).getMessages_received(u2);
    }
}
