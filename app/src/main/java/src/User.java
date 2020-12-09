package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
    private String username;
    private String name;
    private String password;
    private ArrayList<String> messenger_list = new ArrayList<>();
    private ArrayList<String> eventsAttending;
    private Map<String, ArrayList<String>> messages_received = new HashMap<>();
    private Map<String, ArrayList<String>> messages_sent = new HashMap<>();

    /**
     * Constructor for the User entity
     * @param un  the username of the User
     * @param pw  the password of the User's Account
     */
    public User(String un, String pw){
        username = un;
        password = pw;
        eventsAttending = new ArrayList<>();
    }

    /**
     * Sets the name of this user to "name"
     * No return value
     * @param name  the new name of the User
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the name of the User
     * @return  the name of the user
     */
    public String getName(){
        return name;
    }

    /**
     * Returns the username of the User which is a unique identifier
     * for this User
     * @return  the username of the given User
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the password of the User which is a unique identifier
     * for this User
     * @return the password of the given User
     */
    public String getPassword() {return password;}

    /**
     * Adds an event name to the list of events attended by the user
     * The event name uniquely identifies each event
     * No return value
     * @param event_name an event name
     */

    public void addEventAttending(String event_name){
        eventsAttending.add(event_name);
    }

    /**
     * Returns list of events attended by User.
     * Each event is denoted by Event ID rather than Event object
     * @return  the list of event IDs for the event the user plans to attend
     */
    public ArrayList<String> getEventsAttending(){
        return eventsAttending;
    }

    /**
     * Returns a list of usernames identifying each user
     * this user can message
     * @return  the list of usernames corresponding to the users
     * who can be messaged
     */
    public ArrayList<String> getMessengerList(){
        return messenger_list;
    }

    /**
     * Adds a username to the list of usernames corresponding
     * to the users who can be messaged
     * Username uniquely identifies a user
     * No return value
     * @param un  the username of the user to be added
     */
    public void addMessenger(String un){
        if (!this.messenger_list.contains(un)){
            messenger_list.add(un);
        }
    }

    /**
     * Adds a message ID to the list of messages sent, corresponding
     * to a message sent by the user
     * Message ID uniquely identifies a message
     * No return value
     * @param un the username of the user who the message was sent to
     * @param mess_id  the message ID of the message to be added
     */
    public void addMessageSent(String un, String mess_id){
        if (messages_sent.containsKey(un)){
            messages_sent.get(un).add(mess_id);
        }
        else{
            ArrayList<String> mIds = new ArrayList<>();
            mIds.add(mess_id);
            messages_sent.put(un, mIds);
        }
    }

    /**
     * Adds a message ID to the list of messages received, corresponding
     * to a message received by the user
     * Message ID uniquely identifies a message
     * No return value
     * @param un the username of the user that the message came from
     * @param mess_id  the message ID of the message to be added
     */
    public void addMessageReceived(String un, String mess_id){
        if (messages_received.containsKey(un)){
            messages_received.get(un).add(mess_id);
        }
        else {
            ArrayList<String> mIds = new ArrayList<>();
            mIds.add(mess_id);
            messages_received.put(un, mIds);
        }
    }

    /**
     * Returns true iff username un is in the list of usernames in
     * messenger_list i.e. the user corresponding with the username
     * is "friends" with this User
     * @param un the username of the user who has to be messaged
     * @return   true if the conditions are met, false otherwise
     */
    public boolean canMessage(String un){
        return messenger_list.contains(un);
    }

    /**
     * Returns a list of all messages received by this user from another, specific user.
     * @param from  the username of a specific user whose messages this user wants to read
     * @return  an ArrayList of messages received from the user "from"
     */
    public ArrayList<String> getMessages_received(String from){
        return messages_received.get(from);
    }

    public Map<String, ArrayList<String>> getMessages_received2(){
        return messages_received;
    }

    /**
     * Returns a list of all messages sent to a specific user.
     * @param un  the username of the user to whom the messages were sent.
     * @return  an ArrayList of messages sent to the user "un"
     */
    public ArrayList<String> getMessages_sent(String un){ return messages_sent.get(un); }

    public Map<String, ArrayList<String>> getAllMessagesReceived() {
        return this.messages_received;
    }

    public String removeMessageReceived(String id) {
        String key = "";
        for(String username: messages_received.keySet()) {
            if (messages_received.get(username).contains(id)) {
                key = username;
            }
        }
        messages_received.get(key).remove(id);
        return key;
    }

    public String removeMessageSent(String id) {
        String key = "";
        for(String username: messages_sent.keySet()) {
            if (messages_sent.get(username).contains(id)) {
                key = username;
            }
        }
        messages_sent.get(key).remove(id);
        return key;
    }

}