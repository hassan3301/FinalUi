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
        this.commonPrintsPresenter = new CommonPrintsPresenter();
        oc = new OrganizerController("");
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
     * Prompts user to enter log in credentials. If they are valid and accepted,
     * the user is granted access to their corresponding controller (if an attendee
     * logs in, calls the attendee controller for further actions).
     * If login credentials are invalid, requests log in details again.
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void mainMenu() throws ClassNotFoundException, IOException {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            this.commonPrintsPresenter.printWelcomeMenu();
            choice = oc.getValidInput(1, 5);
            if (choice == 5) {
                System.exit(1);
            }
            this.commonPrintsPresenter.printRequestUsername();
            String username = sc.nextLine();
            this.commonPrintsPresenter.printRequestPassword();
            String password = sc.nextLine();
            if (choice == 4) {
                UserAccount.unToVip.clear();
                UserAccount.unToAttendee.clear();
                UserAccount.unToSpeaker.clear();
                UserAccount.unToOrganizer.clear();
                EventManager.EventList.clear();
                if (this.login(username, password)) {
                    System.out.println("The organizers are:");
                    ua.printUser("organizer");
                    System.out.println("The attendees are:");
                    ua.printUser("attendee");
                    System.out.println("The speakers are:");
                    ua.printUser("speaker");
                    commonPrintsPresenter.printSuccessfulLogin();
                    if (ua.unToSpeaker.containsKey(username)) {
                        scon = new SpeakerController(username);
                        scon.chooseAction();
                        this.logout();
                    } else if (ua.unToOrganizer.containsKey(username)) {
                        oc = new OrganizerController(username);
                        oc.chooseAction();
                        this.logout();
                    } else {
                        ac = new AttendeeController(username);
                        ac.chooseAction();
                        this.logout();
                    }
                } else {
                    commonPrintsPresenter.printInvalidLogin();
                }
            } else {
                if (choice == 3) {
                    oc = new OrganizerController(username);
                    oc.createNewAccount(username, password);
                } else if (choice == 2) {
                    ac = new AttendeeController(username);
                    ac.createNewAccount(username, password);
                } else {
                    scon = new SpeakerController(username);
                    scon.createNewAccount(username, password);
                }
                this.sg.WriteToFile("EventFile.ser");
                this.sg.WriteToFile("MessagesFile.ser");
                this.sg.WriteToFile("RoomFile.ser");
                this.sg.WriteToFile("AttendeeFile.ser");
                this.sg.WriteToFile("SpeakerFile.ser");
                this.sg.WriteToFile("OrganizerFile.ser");
                this.sg.WriteToFile("VIPFile.ser");
            }
        } while(choice != 5);
    }


    /**
     * Returns true iff the user is able to successfully log into the Tech Conference System.
     * Compares the entered username and password to those stored in the UserFile.
     * @param username  the username entered by the user
     * @param password  the password of the user's account
     * @return true iff the above conditions are met i.e. login was successful, false otherwise.
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
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

    private void populate_maps() throws IOException, ClassNotFoundException {
        UserAccount ua = new UserAccount();
        List<Event> EventArray = this.sg.readFrom("EventFile.ser", "Event");
        List<Message> MessagesArray = this.sg.readFrom("MessagesFile.ser", "Message");
        List<Room> RoomArray = this.sg.readFrom("RoomFile.ser", "Room");
        for(Event e : EventArray){
            EventManager.EventList.put(e.getName(), e);
        }
        for(Message m: MessagesArray){
            UserAccount.idToMessage.put(m.getId(), m);
        }
        for(Room r: RoomArray) {
            RoomManager.nameToRoom.put(r.getName(), r);
        }
    }

    /**
     * Allows user to log out of their account. Saves all changes to events, messages and users
     * to their corresponding .ser files.
     * @throws IOException throws IO exception if file isn't fount
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
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
