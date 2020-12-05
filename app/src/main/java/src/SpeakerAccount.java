package src;

import java.io.Serializable;
import java.util.ArrayList;

public class SpeakerAccount extends UserAccount implements MessageManager, Serializable {

    /**
     * The SpeakerAccount Constructor.
     */
    public SpeakerAccount(){}

    /**
     * Add a new speaker object to the map of speakers
     * @param userName the username for the speaker
     * @param password the password for the speaker
     */
    public void addSpeaker(String userName, String password) {
        Speaker spe = new Speaker(userName, password);
        unToSpeaker.put(userName, spe);
    }

    /**
     * Determine if the speaker with username username exists in the map of speaker
     * @param username the username of the speaker we wan to check
     * @return true iff the map of speakers contains the speaker with username username
     */
    public boolean speakerExists(String username) {
        return unToSpeaker.containsKey(username);
    }

    /**
     * Return the list of events that the speaker with username userName will speak at.
     * @param userName the username of the speaker whose events you want to return
     * @return the list of events that the speaker will speak at.
     */
    public ArrayList<String> getEvents(String userName) {
        return unToSpeaker.get(userName).getSpeakingAt();
    }

    /**
     * Assign the speaker with username userName to speak at the event with name event.
     * @param userName the username of the speaker
     * @param event the name of the event
     */
    public void addEventToSpeaker(String userName, String event) {
        unToSpeaker.get(userName).assignSpeaking(event);
    }


    /**
     * Send a message to all attendees of an event
     * @param from the username of the speaker sending the message
     * @param event_name the name of the event that's attendees are being sent a message
     * @param m_id the id of the message being sent
     */
    public void messageEventAttendees(String from, String event_name, String m_id){
        for(String to : EventManager.EventList.get(event_name).attendee_list){
            unToSpeaker.get(from).addMessageSent(to, m_id);
            unToAttendee.get(to).addMessageReceived(from, m_id);
        }
    }


    /**
     * Sends a message from a speaker to either another speaker or attendee
     * @param from the username of the speaker sending the message
     * @param to the username of the user receiving the message
     * @param m_id the id of the message being sent
     */
    @Override
    public void sendTo(String from, String to, String m_id) {
        if (unToSpeaker.containsKey(to)) {
            unToSpeaker.get(from).addMessageSent(to, m_id);
            unToSpeaker.get(to).addMessageReceived(from, m_id);
        } else if (unToAttendee.containsKey(to)) {
            unToSpeaker.get(from).addMessageSent(to, m_id);
            unToAttendee.get(to).addMessageReceived(from, m_id);
        }
    }

    /**
     * Shows the user a list of messages from another user
     * @param u1 the username of the speaker viewing the messages
     * @param u2 the username of the user that sent the messages
     * @return a list of messages
     */
    @Override
    public ArrayList<String> viewMessages(String u1, String u2) {
        return unToSpeaker.get(u1).getMessages_received(u2);
    }
}
