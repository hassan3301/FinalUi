package Entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Speaker extends User implements Serializable {

    private ArrayList<String> speakingAt = new ArrayList<>();


    /**
     * The Speaker Constructor.
     * @param un the speaker's username.
     * @param pw the speaker's password.
     */
    public Speaker(String un, String pw){
        super(un, pw);
    }

    /**
     * Assign this speaker to speak at the given event.
     * @param event_id
     * @return Returns true if the event was successfully assigned, false if not.
     */
    public boolean assignSpeaking (String event_id){
        if(!speakingAt.contains(event_id)){
            speakingAt.add(event_id);
            return true;
        }
        return false;
    }

    /**
     * Removes an event from the list of events that this Speaker is speaking at.
     * @param event_id
     * @return Returns true if the event was successfully removed, valse if not
     */
    public boolean removeSpeaking(String event_id){
        if(speakingAt.contains(event_id)){
            speakingAt.remove(event_id);
            return true;
        }
        return false;
    }

    /**
     * Returns an Arraylist of Strings, the event ids of the events this speaker is speaking at.
     * @return Arraylist<String>
     */
    public ArrayList<String> getSpeakingAt(){
        return speakingAt;
    }



}