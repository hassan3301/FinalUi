package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import src.Attendee;
import src.AttendeeController;
import src.CommonPrintsPresenter;
import src.EventManager;
import src.OrganizerController;
import src.SerializerGateway;
import src.SpeakerController;

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
                if (this.login(username, password)) {
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
                this.sg.WriteToFile("./phase1/src/UserFile.ser");
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
        ArrayList<User> UserArray = new ArrayList<>();
        UserArray = this.sg.readFromUN("./phase1/src/UserFile.ser");
        this.populate_maps(UserArray);
        for (User o : UserArray) {
            if (o.getUsername().equals(username)) {
                return o.getPassword().equals(password);
            }
        }
        return false;
    }
    private void populate_maps(ArrayList<User> array) throws IOException, ClassNotFoundException {
        ArrayList<Event> EventArray = this.sg.readFromEvent("./phase1/src/EventFile.ser");
        ArrayList<Message> MessagesArray = this.sg.readFromMessages("./phase1/src/MessagesFile.ser");
        for (User o : array){
            if(o instanceof Attendee){
                UserAccount.unToAttendee.put(o.getName(), (Attendee) o);
            }
            else if(o instanceof Speaker){
                UserAccount.unToSpeaker.put(o.getName(), (Speaker) o);
            }
            else if(o instanceof Organizer){
                UserAccount.unToOrganizer.put(o.getName(), (Organizer) o);
            }
        }
        for(Event e : EventArray){
            EventManager.EventList.put(e.getName(), e);
        }
        for(Message m: MessagesArray){
            UserAccount.idToMessage.put(m.getId(), m);
        }

    }

    /**
     * Allows user to log out of their account. Saves all changes to events, messages and users
     * to their corresponding .ser files.
     * @throws IOException throws IO exception if file isn't fount
     * @throws ClassNotFoundException throws ClassNotFound exception
     */
    public void logout() throws IOException, ClassNotFoundException {
        this.sg.WriteToFile("./phase1/src/EventFile.ser");
        this.sg.WriteToFile("./phase1/src/MessagesFile.ser");
        this.sg.WriteToFile("./phase1/src/UserFile.ser");
        commonPrintsPresenter.printLoggedOut();
    }
}
