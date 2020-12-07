package src;

import java.io.Serializable;
import java.util.ArrayList;

public class OrganizerAccount extends UserAccount implements MessageManager, Serializable {

    public OrganizerAccount(){}

    /**
     * Creates a new Organiser object and adds it to the organiser username to organiser map
     * @param userName the username of the organiser
     * @param password the password of the organiser's account
     */
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
