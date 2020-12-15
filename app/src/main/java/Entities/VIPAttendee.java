package Entities;

import java.io.Serializable;

import Entities.Attendee;

public class VIPAttendee extends Attendee implements Serializable {

    /**
     * Constructor for  VIP attendee
     * @param username username of the VIP attendee
     * @param password password of the VIP attendee's account
     */
    public VIPAttendee(String username, String password){
        super(username, password);
    }
}