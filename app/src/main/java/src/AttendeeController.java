package src;

import android.widget.Toast;

import com.example.conferenceapp.MainActivity;
import com.example.conferenceapp.MessageAdapter;
import com.example.conferenceapp.MessagePage;

import java.io.IOException;
import java.util.*;

public class AttendeeController extends UserController {
    private AttendeePresenter attendeePresenter;
    private CommonPrintsPresenter commonPrintsPresenter;
    private Scanner sc;

    /**
     * Constructor for an AttendeeController
     *
     * @param username the username of the Attendee currently logged in
     */
    public AttendeeController(String username) {
        super(username);
        this.attendeePresenter = new AttendeePresenter();
        this.commonPrintsPresenter = new CommonPrintsPresenter();
        this.sc = new Scanner(System.in);
    }

    /**
     * Accepts user input and calls the appropriate controller and use case
     * methods to perform the requested operation.
     * Continues to request for further operations until the Attendee
     * selects "log out".
     * Returns nothing (void).
     */
    public void chooseAction() {
        int input;
        do {
            attendeePresenter.chooseActionText();
            input = getValidInput(1, 12);
            switch (input) {
                case 1: {
                    attendeePresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.callSignUp(username, event_name);
                    break;
                }
                case 2: {
                    attendeePresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.callDeleteEvent(username, event_name);
                    break;
                }
                case 3: {
                    viewAllEvents();
                    break;
                }
                case 4: {
                    viewScheduledEventsAttending(username);
                    break;
                }
                case 5: {
                    attendeePresenter.printUsernameView();
                    String un = sc.nextLine();
                    callViewMessages(username, un);
                    break;
                }
                case 6: {
                    attendeePresenter.printUsernameHistory();
                    String un = sc.nextLine();
                    callViewMessages(un, username);
                    break;
                }
                case 7: {
                    attendeePresenter.printUsername();
                    String un = sc.nextLine();
                    attendeePresenter.printSend();
                    String text = sc.nextLine();
                    callSendTo(username, un, text);
                    break;
                }
                case 8: {
                    attendeePresenter.printAddMessagable();
                    String un = sc.nextLine();
                    callAddMessenger(un);
                    break;
                }
                case 9: {
                    String idToChange = markMessageHelper(attendeesAccount.viewAllMessages(this.username),
                            "archived");
                    if (!idToChange.equals("")) {
                        archiveMessage(idToChange);
                    }
                    break;
                }
                case 10: {
                    String idToChange = markMessageHelper(attendeesAccount.viewAllMessages(this.username),
                            "read");
                    if (!idToChange.equals("")) {
                        markMessageUnread(idToChange);
                    }
                    break;
                }
                case 11: {
                    String idToChange = markMessageHelper(attendeesAccount.viewAllMessages(this.username),
                            "deleted");
                    if (!idToChange.equals("")) {
                        userAccount.deleteMessage(idToChange, this.username);
                        commonPrintsPresenter.printSuccessfulMessageDeletion();
                    }
                    break;
                }
            }
        } while (input != 12);
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
        if (!UserAccount.unToAttendee.containsKey(a2)){
            //commonPrintsPresenter.printUserNotFound();
        }
        else if (UserAccount.unToAttendee.get(a1).canMessage(a2)){
            attendeesAccount.sendTo(a1, a2, m.getId());
            //commonPrintsPresenter.printMessageSent();
        }
        else {
            //commonPrintsPresenter.printMessageSendingFailure();
        }
    }

    /**
     * Returns all the messages attendee with username a1 has received from attendee with username a2
     * @param a1 the username of the user who wants to read the messages
     * @param a2 the username of the user who has sent the messages
     */
    public void callViewMessages(String a1, String a2){
        if(attendeesAccount.viewMessages(a1, a2) == null) {
            commonPrintsPresenter.printUserNotFound();
        }
        else if(attendeesAccount.viewMessages(a1, a2).isEmpty()){
            commonPrintsPresenter.printEmptyMessages();
        }
        else {
            commonPrintsPresenter.viewMessages(attendeesAccount.viewMessages(a1, a2));
        }
    }

    /**
     * Creates a new Attendee Account with the given username
     * and password
     * @param username the username of the new user's account
     * @param password the password of the new user's account
     */
    public void createNewAccount(String username, String password) {
        attendeesAccount.addAttendee(username, password);
        //commonPrintsPresenter.printSuccessfulAccountCreation();
    }

    /**
     * Calls the add messenger method from attendees account
     * @param u2 the username of the user that you would like to add to your messenger list
     */
    public void callAddMessenger(String u2){
        attendeesAccount.addMessenger(username, u2);
        //attendeePresenter.printSuccessfulAdd();
    }
}