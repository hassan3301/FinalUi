package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AttendeeController {
    private String username;
    private AttendeesAccount attendeesAccount;
    private EventManager eventManager;
    private AttendeePresenter attendeePresenter;
    private CommonPrintsPresenter commonPrintsPresenter;
    private Scanner sc;

    /**
     * Constructor for an AttendeeController
     * @param username the username of the Attendee currently logged in
     */
    public AttendeeController(String username){
        this.username = username;
        attendeesAccount = new AttendeesAccount();
        eventManager = new EventManager();
        attendeePresenter = new AttendeePresenter();
        commonPrintsPresenter = new CommonPrintsPresenter();
        sc = new Scanner(System.in);
    }

    /**
     * Accepts user input and calls the appropriate controller and use case
     * methods to perform the requested operation.
     * Continues to request for further operations until the Attendee
     * selects "log out".
     * Returns nothing (void).
     */
    public void chooseAction() throws IOException, ClassNotFoundException {
        String input;
        do {
            attendeePresenter.chooseActionText();
            input = sc.nextLine();
            if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") &&
                    !input.equals("5") && !input.equals("6") && !input.equals("7") && !input.equals("8")) {
                commonPrintsPresenter.printInvalidOption();
            } else {
                switch (input) {
                    case "1": {
                        attendeePresenter.printEventName();
                        String event_name = sc.nextLine();
                        this.callSignUp(username, event_name);
                        break;
                    }
                    case "2": {
                        attendeePresenter.printEventName();
                        String event_name = sc.nextLine();
                        this.callDeleteEvent(username, event_name);
                        break;
                    }
                    case "3": {
                        viewAllEvents();
                        break;
                    }
                    case "4": {
                        viewScheduledEvents(username);
                        break;
                    }
                    case "5": {
                        attendeePresenter.printUsernameView();
                        String un = sc.nextLine();
                        callViewMessages(username, un);
                        break;
                    }
                    case "6": {
                        attendeePresenter.printUsernameHistory();
                        String un = sc.nextLine();
                        callViewMessages(un, username);
                        break;
                    }
                    case "7": {
                        attendeePresenter.printUsername();
                        String un = sc.nextLine();
                        attendeePresenter.printSend();
                        String text = sc.nextLine();
                        callSendTo(username, un, text);
                        break;
                    }
                    case "8":{
                        attendeePresenter.printAddMessagable();
                        String un = sc.nextLine();
                        callAddMessenger(un);
                        break;
                    }
                }
            }
        } while(!input.equals("9"));
    }

    /**
     * Allows Attendee to sign up for an event. Prints an output message to notify
     * whether sign up was successful or not. Returns nothing (void).
     * @param username  The username of the attendee object that wishes to sign up
     *                  for the event
     * @param event_name The name of the event (String) that the Attendee
     *                   wishes to sign up for
     */
    public void callSignUp(String username, String event_name){
        boolean done;
        if (attendeesAccount.canSignUp(username, event_name)){
            Attendee attendee = UserAccount.unToAttendee.get(username);
            attendee.addEvent(event_name);
            eventManager.update_attendees(EventManager.EventList.get(event_name), attendee);
            done = true;
        }
        else{
            done = false;
        }
        if (done){
            commonPrintsPresenter.printSuccessfulSignUp();
        }
        else{
            commonPrintsPresenter.printFailedSignUp();
        }
    }

    /**
     * Allows Attendee to delete an event from its schedule. Prints an appropriate
     * output message depending on whether the deletion was successful. Returns
     * nothing (void)
     * @param username   The username of the attendee object that wishes to delete
     *                   the chosen event
     * @param event_name The name of the event (String) that has
     *                   to be deleted
     */
    public void callDeleteEvent(String username, String event_name){
        boolean done;
        if (attendeesAccount.canDeleteEvent(username, event_name)){
            Attendee attendee = UserAccount.unToAttendee.get(username);
            attendee.getEvents().remove(event_name);
            EventManager.EventList.get(event_name).getAttendee_list().remove(attendee.getUsername());
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
     * attendee has signed up for
     * @param username  The username of the attendee object/user who wishes to
     *                  see all the events they have signed up
     *                  for
     * @return  String containing all event names of the events attendee has
     * signed up for
     */
    public void viewScheduledEvents(String username){
        commonPrintsPresenter.printAllEvents(attendeesAccount.seeScheduledEvents(username));
    }

    /**
     * Returns the toString (string format) of all events in the
     * system, regardless of whether the Attendee has signed up for it
     * @return  String containing details of all events in the system
     */
    public void viewAllEvents(){
        StringBuilder allEvents = new StringBuilder();
        for (Event event: EventManager.EventList.values()){
            allEvents.append(event.toString()).append("\n");
        }
        commonPrintsPresenter.printAllEvents(allEvents.toString());
    }

    /**
     * Allows an attendee to send a message to another. Prints
     * a confirmation message if the message was sent, otherwise prints
     * a failure statement.
     * @param a1 the username of the attendee who wishes to send the message
     * @param a2  the username of the attendee who must receive the message
     * @param text the message body
     */
    public void callSendTo(String a1, String a2, String text){
        Message m = attendeesAccount.createMessage(text);
        if (UserAccount.unToAttendee.get(a1).canMessage(a2)){
            attendeesAccount.sendTo(a1, a2, m.getId());
            commonPrintsPresenter.printMessageSent();
        }
        else {
            commonPrintsPresenter.printMessageSendingFailure();
        }
    }

    /**
     * Returns all the messages attendee with username a1 has received from attendee with username a2
     * @param a1 the username of the user who wants to read the messages
     * @param a2 the username of the user who has sent the messages
     * @return an ArrayList of all messages received by a1 from a2
     */
    public void callViewMessages(String a1, String a2){
       commonPrintsPresenter.viewMessages(attendeesAccount.viewMessages(a1, a2));
    }

    public void createNewAccount(String username, String password) {
        attendeesAccount.addAttendee(username, password);
        commonPrintsPresenter.printSuccessfulAccountCreation();
    }

    /**
     * Calls the add messenger method from attendees account
     * @param u2 the username of the user that you would like to add to your messenger list
     */
    public void callAddMessenger(String u2){
        attendeesAccount.addMessenger(username, u2);
        attendeePresenter.printSuccessfulAdd();
    }
}
