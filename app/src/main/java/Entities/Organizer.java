package Entities;

import java.io.Serializable;

public class Organizer extends User implements Serializable {
    /**
     * The Organizer's constructor
     * @param un organizer's username
     * @param pw organizer account's password
     */
    public Organizer(String un, String pw) {
        super(un, pw);
    }
}
