package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TechConferenceController {
    private SerializerGateway sg;
    private UserAccount ua;
    private EventManager em;
    private CommonPrintsPresenter commonPrintsPresenter;
    private AttendeeController ac;
    private OrganizerController oc;
    private SpeakerController scon;
    private Attendee a;

    /**
     * The Constructor for the Tech Conference System's Controller.
     * Launches the log in screen.
     */
    public TechConferenceController(){
        this.sg = new SerializerGateway();
        this.ua = new UserAccount();
        this.em = new EventManager();
        this.commonPrintsPresenter = new CommonPrintsPresenter();
        oc = new OrganizerController("");

    }

    public AttendeeController getAc() {
        return ac;
    }

    public void setAc(AttendeeController ac){
        this.ac = ac;
    }

    public OrganizerController getOC() {return oc;}

    public SpeakerController getScon() {return scon;}

    public void setScon(SpeakerController scon){this.scon = scon;}

    public void setOc(OrganizerController oc){this.oc = oc;}



    /**
     * Prompts user to enter log in credentials. If they are valid and accepted,
     * the user is granted access to their corresponding controller (if an attendee
     * logs in, calls the attendee controller for further actions).
     * If login credentials are invalid, requests log in details again.
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
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
    public boolean login(String username, String password) throws ClassNotFoundException, IOException {
        List<Organizer> OrganizerArray = new ArrayList<>();
        OrganizerArray = this.sg.readFrom("OrganizerFile.ser", "Organizer");
        List<Speaker> SpeakerArray = new ArrayList<>();
        SpeakerArray = this.sg.readFrom("SpeakerFile.ser", "Speaker");
        List<Attendee> AttendeeArray = new ArrayList<>();
        AttendeeArray = this.sg.readFrom("AttendeeFile.ser", "Attendee");
        List<VIPAttendee> VIPArray = new ArrayList<>();
        VIPArray = this.sg.readFrom("VIPFile.ser", "VIP Attendee");
        this.populate_maps();
        for (Organizer o : OrganizerArray) {
            UserAccount.unToOrganizer.put(o.getUsername(), o);
        }
        for (Speaker o : SpeakerArray) {
            UserAccount.unToSpeaker.put(o.getUsername(), o);
        }
        for (Attendee o : AttendeeArray) {
            UserAccount.unToAttendee.put(o.getUsername(), o);
        }
        for (VIPAttendee o : VIPArray) {
            UserAccount.unToVip.put(o.getUsername(), o);
        }
        List<User> UserArray = new ArrayList<>();
        UserArray.addAll(OrganizerArray);
        UserArray.addAll(SpeakerArray);
        UserArray.addAll(AttendeeArray);
        UserArray.addAll(VIPArray);
        for(User u: UserArray) {
            if(u.getUsername().equals(username)) {
                return u.getPassword().equals(password);
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
        this.sg.WriteToFile("EventFile.ser");
        this.sg.WriteToFile("MessagesFile.ser");
        this.sg.WriteToFile("RoomFile.ser");
        this.sg.WriteToFile("AttendeeFile.ser");
        this.sg.WriteToFile("SpeakerFile.ser");
        this.sg.WriteToFile("OrganizerFile.ser");
        this.sg.WriteToFile("VIPFile.ser");
        commonPrintsPresenter.printLoggedOut();
    }
}
