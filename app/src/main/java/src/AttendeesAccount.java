package src;

import java.io.Serializable;
import java.util.ArrayList;

public class AttendeesAccount extends UserAccount implements MessageManager, Serializable {

    /**
     * Constructor for AttendeesAccount
     */
    public AttendeesAccount(){}

    /**
     * Creates an Attendee entity and adds it to the map.
     * Called by the Login Portal when an Attendee logs in
     * @param username  the username of the Attendee
     * @param password  the password of the Attendee
     */
    public void addAttendee(String username, String password) {
        Attendee att = new Attendee(username, password);
        unToAttendee.put(username, att);
    }

    /**
     * Adds a user to the messenger list of u1
     * @param u1 the username of the user to whose messenger list we are adding
     * @param u2 the username of the user being added to the list
     */
    public void addMessenger(String u1, String u2){
        if (unToAttendee.containsKey(u2)){
            unToAttendee.get(u1).addMessenger(u2);
        }
        else{
            System.out.println("This user does not exist");
        }
    }

    /**
     * Send a message from one user to another
     * @param from The username of the user sending the message
     * @param to A list of usernames of the users to which this message will be sent(in this case just one user)
     * @param m_id The id of the message being sent
     */
    @Override
    public void sendTo(String from, String to, String m_id) {
        if (unToAttendee.get(from).getMessengerList().contains(to)){
            unToAttendee.get(from).addMessageSent(to, m_id);
            unToAttendee.get(to).addMessageReceived(from, m_id);
        }
        else{
            System.out.println("The user is not on your messenger list");
        }
    }

    /**
     * Displays the messages received of a user from another user
     * @param u1 The username of the user who has received the messages
     * @param u2 The username of the user who has sent the messages
     * @return Return an arraylist of messages received by u1 from u2
     */
    @Override
    public ArrayList<String> viewMessages(String u1, String u2) {
        return unToAttendee.get(u1).getMessages_received(u2);
    }

}