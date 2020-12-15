package UseCase;

import java.util.ArrayList;


public interface MessageManager {

    /**
     * A void method to send a message that is implemented in attendee, organizer, and speaker account
     * @param from the username of the user sending a message
     * @param to the username of the user receiving the message
     * @param m_id the id of the message being sent
     */
    void sendTo(String from, String to, String m_id);

    ArrayList<String> viewMessages(String u1, String u2);

 }
