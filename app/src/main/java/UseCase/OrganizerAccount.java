package UseCase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import Entities.Organizer;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void makeOrganizer(String username, String password, Map<String, ArrayList<String>> messages_recieved, Map<String, ArrayList<String>> AllMessagesRecieved, ArrayList<String> events_attending, ArrayList<String> messenger_list){
        Organizer organizer = new Organizer(username, password);
        events_attending.forEach(organizer::addEventAttending);
        messenger_list.forEach(organizer::addMessenger);
        messages_recieved.forEach((k, v)->{
            for(String msg : v){
                organizer.addMessageReceived(k, msg);
            }
        });
        UserAccount.unToOrganizer.put(username, organizer);

    }

    /**
     * Sends a message from an organizer to all other organizers
     * @param from the username of the organizer this message is being sent from
     * @param m_id the id of the message being sent to the speaker
     */
    public void sendToOrganizers(String from, String m_id){
        for (String un: unToOrganizer.keySet()){
            if(!un.equals(from)){
                unToOrganizer.get(from).addMessageSent(un, m_id);
                unToOrganizer.get(un).addMessageSent(from, m_id);
            }
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
}
