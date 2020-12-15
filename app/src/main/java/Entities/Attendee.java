package Entities;

import java.io.Serializable;

public class Attendee extends User implements Serializable {

    /**
     * Constructor for Attendee. Inherits the constructor for User.
     * @param un this is the username of the attendee
     * @param pw this is the password of the attendee's account
     */
    public Attendee(String un, String pw) {
        super(un, pw);
    }
}
