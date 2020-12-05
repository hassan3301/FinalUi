package src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*AttendeesAccount a = new AttendeesAccount();
        a.createAttendee("t1", "s1");
        a.createAttendee("t2", "s2");
        UserAccount.unToAttendee.get("t1").setName("t1");
        UserAccount.unToAttendee.get("t1").addMessenger("t2");
        UserAccount.unToAttendee.get("t1").addEvent("e1");

        EventManager em = new EventManager();
        em.createEvent("e1", "r1", "s1", "event", Calendar.getInstance(), Calendar.getInstance());

        em.update_attendees(EventManager.EventList.get("e1"), UserAccount.unToAttendee.get("t1"));
        AttendeeController ac = new AttendeeController("t1");
        ac.attendeesAccount = a;
        ac.eventManager = em;
        TechConferenceController tc = new TechConferenceController();
        tc.mainMenu();
        //EventManager.EventList = EventManager.EventList;*/
        TechConferenceController tcc = new TechConferenceController();
        tcc.mainMenu();
    }

}
