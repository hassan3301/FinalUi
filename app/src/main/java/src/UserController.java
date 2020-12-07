package src;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserController {
    public String username;
    public AttendeesAccount attendeesAccount;
    public EventManager eventManager;
    public CommonPrintsPresenter commonPrintsPresenter;
    public OrganizerAccount oa;
    public SpeakerAccount speakeraccount;
    public VIPAttendeeAccount vip;
    public UserAccount userAccount;
    public Scanner sc;

    /**
     * Constructor for the User Controller
     * @param username
     */
    public UserController(String username){
        this.username = username;
        this.attendeesAccount = new AttendeesAccount();
        this.eventManager = new EventManager();
        this.commonPrintsPresenter = new CommonPrintsPresenter();
        this.oa = new OrganizerAccount();
        this.speakeraccount = new SpeakerAccount();
        this.vip = new VIPAttendeeAccount();
        this.userAccount = new UserAccount();
        this.sc = new Scanner(System.in);
    }

    /**
     * Returns the user account for the username entered.
     * If the username corresponds to an attendee, returns this Controller's
     * attendee account and so on.
     * @param username the username of the user who has logged in
     * @return the user (attendee, speaker, VIPattendee, organiser) account of the user with the given username
     */
    public UserAccount getUserAccount(String username){
        if (userAccount.getUser(username).getClass().getName().equals("Attendee")){
            return this.attendeesAccount;
        }
        else if (userAccount.getUser(username).getClass().getName().equals("Speaker")){
            return this.speakeraccount;
        }
        else if (userAccount.getUser(username).getClass().getName().equals("VIPAttendee")){
            return this.vip;
        }
        else{
            return this.oa;
        }

    }

    /**
     * Allows the user to sign up for an event. Prints an output message to notify
     * whether sign up was successful or not. Returns nothing (void).
     * @param username  The username of the user object that wishes to sign up
     *                  for the event
     * @param event_name The name of the event (String) that the User
     *                   wishes to sign up for
     */
    public void callSignUp(String username, String event_name) {
        boolean done;
        if (getUserAccount(username).canSignUp(username, event_name)) {
            userAccount.getUser(username).addEventAttending(event_name);
            eventManager.update_attendees(EventManager.EventList.get(event_name), userAccount.getUser(username));
            done = true;
        } else {
            done = false;
        }
        if (done) {
            commonPrintsPresenter.printSuccessfulSignUp();
        } else {
            commonPrintsPresenter.printFailedSignUp();
        }
    }

    /**
     * Allows User to delete an event they are attending from its schedule. Prints an appropriate
     * output message depending on whether the deletion was successful. Returns
     * nothing (void)
     * @param username   The username of the user object that wishes to delete
     *                   the chosen event
     * @param event_name The name of the event (String) that has
     *                   to be deleted
     */
    public void callDeleteEvent(String username, String event_name){
        boolean done;
        if (getUserAccount(username).canDeleteEvent(username, event_name)){
            userAccount.getUser(username).getEventsAttending().remove(event_name);
            EventManager.EventList.get(event_name).getAttendee_list().remove(username);
            done = true;
        }
        else {
            done = false;
        }
        if (done){
            commonPrintsPresenter.printSuccessfulDeletion();
        }
        else{
            commonPrintsPresenter.printFailedDeletion();
        }
    }

    /**
     * Return the list of event names for all the events
     * user has signed up for
     * @param username  The username of the user who wishes to
     *                  see all the events they have signed up
     *                  for
     */
    public void viewScheduledEventsAttending(String username){
        commonPrintsPresenter.printAllEvents(getUserAccount(username).seeScheduledEvents(username));
    }

    /**
     * Returns the toString (string format) of all events in the
     * system, regardless of whether the user has signed up for it
     */
    public void viewAllEvents(){
        StringBuilder allEvents = new StringBuilder();
        for (Event event: EventManager.EventList.values()){
            allEvents.append(event.toString()).append("\n");
        }
        commonPrintsPresenter.printAllEvents(allEvents.toString());
    }

    /**
     * Returns an integer representing user input between min and max (inclusive)
     * @param min the minimum integer for the input
     * @param max the maximum integer for the input
     * @return an integer between min and max (inclusive) corresponding to the user's input.
     */
    public int getValidInput(int min, int max) {
        int input = -1;
        boolean done = false;
        while (!done) {
            input = -1;
            try {
                commonPrintsPresenter.printEnterNumber(min, max);
                input = sc.nextInt();
            }
            catch (InputMismatchException e) {
                sc.nextLine();
            }
            if(min <= input && max >= input) {
                done = true;
            }
        }
        return input;
    }
}