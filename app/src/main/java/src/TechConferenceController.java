package src;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class TechConferenceController {
    private SerializerGateway sg;
    private UserAccount ua;
    private AttendeesAccount aa;
    private EventManager em;
    private CommonPrintsPresenter commonPrintsPresenter;
    private AttendeeController ac;
    private VIPAttendeeController vac;
    private OrganizerController oc;
    private SpeakerController scon;
    private RoomManager rm;
    private Attendee a;

    /**
     * The Constructor for the Tech Conference System's Controller.
     * Launches the log in screen.
     */
    public TechConferenceController(){
        this.sg = new SerializerGateway();
        this.ua = new UserAccount();
        this.em = new EventManager();
        this.rm = new RoomManager();
        this.aa = new AttendeesAccount();
        this.commonPrintsPresenter = new CommonPrintsPresenter();
        oc = new OrganizerController("");
        ac = new AttendeeController("");
        vac = new VIPAttendeeController("");

        scon = new SpeakerController("");

    }

    public AttendeeController getAc() { return ac; }
    public SpeakerController getSC(){return scon;}
    public EventManager getEm(){return em;}
    public RoomManager getRm(){return rm;}
    public VIPAttendeeController getVIP(){return vac;}

    public void setAc(AttendeeController ac){
        this.ac = ac;
    }

    public OrganizerController getOC() {return oc;}

    public SpeakerController getScon() {return scon;}

    public void setScon(SpeakerController scon){this.scon = scon;}

    public void setOc(OrganizerController oc){this.oc = oc;}

    public void setVac(VIPAttendeeController vac){this.vac = vac;};

    public VIPAttendeeController getVac() {return vac;}




    /**
     * Returns true iff the user is able to successfully log into the Tech Conference System.
     * Compares the entered username and password to those stored in the UserFile.
     * @param username  the username entered by the user
     * @param password  the password of the user's account
     * @return true iff the above conditions are met i.e. login was successful, false otherwise.
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
    public boolean login(String username, String password) throws ClassNotFoundException, IOException {
        for (Map.Entry<String,Attendee> entry : UserAccount.unToAttendee.entrySet()){
            if (entry.getValue().getUsername().equals(username)){
                return entry.getValue().getPassword().equals(password);
            }
        }
        for (Map.Entry<String,Organizer> entry : UserAccount.unToOrganizer.entrySet()){
            if (entry.getValue().getUsername().equals(username)){
                return entry.getValue().getPassword().equals(password);
            }
        }
        for (Map.Entry<String,Speaker> entry : UserAccount.unToSpeaker.entrySet()){
            if (entry.getValue().getUsername().equals(username)){
                return entry.getValue().getPassword().equals(password);
            }
        }
        for (Map.Entry<String,VIPAttendee> entry : UserAccount.unToVip.entrySet()){
            if (entry.getValue().getUsername().equals(username)){
                return entry.getValue().getPassword().equals(password);
            }
        }
        return false;
    }


    /**
     * Allows user to log out of their account. Saves all changes to events, messages and users
     * to their corresponding .ser files.
     * @throws IOException throws IO exception if file isn't fount
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void logout() throws IOException, ClassNotFoundException {
        FirebaseGateway.WriteToDB("TCC", UserAccount.unToAttendee, "Attendee");
        FirebaseGateway.WriteToDB("TCC", UserAccount.unToOrganizer, "Organizer");
        FirebaseGateway.WriteToDB("TCC", UserAccount.unToSpeaker, "Speaker");
        FirebaseGateway.WriteToDB("TCC", UserAccount.unToOrganizer, "Organizer");
        FirebaseGateway.WriteToDB("TCC", UserAccount.unToVip, "VIP");
        FirebaseGateway.WriteToDB("TCC", UserAccount.idToMessage, "Message");
        FirebaseGateway.WriteToDB("TCC", EventManager.EventList, "Events");
        FirebaseGateway.WriteToDB("TCC", RoomManager.nameToRoom, "Room");
        commonPrintsPresenter.printLoggedOut();
    }
}
