package src;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SpeakerController extends UserController{

    private SpeakerPresenter speakerpresenter;
    private CommonPrintsPresenter commonprints;

    /**
     * Constructor for SpeakerController
     * @param username the username of the Speaker who is currently logged in.
     */
    public SpeakerController(String username){
        super(username);
        speakerpresenter = new SpeakerPresenter();
        commonprints = new CommonPrintsPresenter();
    }

    /**
     * Accepts user input and calls the appropriate controller and use case
     * methods to perform the requested operation.
     * Continues to request for further operations until the Attendee
     * selects "log out".
     * Returns nothing (void).
     *
     * @throws IOException if file not found
     * @throws ClassNotFoundException if class not found
     */
    public void chooseAction() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        do {
            speakerpresenter.chooseActionText();
            input = getValidInput(1, 13);
            switch (input) {
                case 1: {
                    viewScheduledEvents(username);
                    break;
                }
                case 2: {
                    commonPrintsPresenter.printViewMessageUserName();
                    String un = sc.nextLine();
                    if(username.equals(un)) {
                        System.out.println("Sorry, you can't message yourself!");
                    }
                    else if(speakeraccount.viewMessages(this.username, un) != null){
                        callViewMessages(un);
                    }
                    else {
                        commonPrintsPresenter.printUserNotFound();
                    }
                    break;
                }
                case 3: {
                    commonPrintsPresenter.printMessageUserName();
                    String un = sc.nextLine();
                    commonPrintsPresenter.printSend();
                    String text = sc.nextLine();
                    callSendTo(un, text);
                    break;
                }
                case 4: {
                    commonPrintsPresenter.printEventName();
                    String eventName = sc.nextLine();
                    if(speakeraccount.getEvents(username).contains(eventName)){
                        commonPrintsPresenter.printSend();
                        String text = sc.nextLine();
                        callMessageEventAttendees(eventName, text);
                    }
                    else {
                        commonPrintsPresenter.printEventNotFound();
                    }
                    break;
                }
                case 5: {
                    if(speakeraccount.getEvents(username).size() == 0){
                        commonPrintsPresenter.printNoEventsScheduled();
                    }
                    else {
                        commonPrintsPresenter.printMessageText();
                        String text = sc.nextLine();
                        for (String event : speakeraccount.getEvents(username)) {
                            callMessageEventAttendees(event, text);
                        }
                        commonPrintsPresenter.printMessageSent();

                    }
                    break;
                }
                case 6: {
                    String idToChange = markMessageHelper(speakeraccount.viewAllMessages(this.username),
                            "archived");
                    if (!idToChange.equals("")) {
                        archiveMessage(idToChange);
                    }
                    break;
                }
                case 7: {
                    String idToChange = markMessageHelper(speakeraccount.viewAllMessages(this.username),
                            "read");
                    if (!idToChange.equals("")) {
                        markMessageUnread(idToChange);
                    }
                    break;
                }
                case 8: {
                    String idToChange = markMessageHelper(speakeraccount.viewAllMessages(this.username),
                            "deleted");
                    if (!idToChange.equals("")) {
                        userAccount.deleteMessage(idToChange, this.username);
                        commonPrintsPresenter.printSuccessfulMessageDeletion();
                    }
                    break;
                }
                case 9: {
                    speakerpresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.callSignUp(username, event_name);
                    break;
                }
                case 10: {
                    speakerpresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.callDeleteEvent(username, event_name);
                    break;
                }
                case 11: {
                    viewAllEvents();
                    break;
                }
                case 12: {
                    viewScheduledEventsAttending(username);
                    break;
                }
                }
            }while (input != 13);
        }

    /**
     * Creates new speaker account, and prints a message upon successful creation
     * @param username the username of the speaker to be created
     * @param password the password of the speaker to be creeated
     */
    public void createNewAccount(String username, String password) {
        speakeraccount.addSpeaker(username, password);
        commonPrintsPresenter.printSuccessfulAccountCreation();
    }

    /**
     * Returns a string of all the events the speaker that is currently logged in
     * is scheduled to speak at.
     */
    public void viewScheduledEvents(String speakerun){
        StringBuilder speaking = new StringBuilder();
        for(String event : UserAccount.unToSpeaker.get(speakerun).getSpeakingAt()){
            speaking.append(event).append("\n");
        }
        commonPrintsPresenter.printAllEvents(speaking.toString());
    }



    public ArrayList<Event> getScheduledEvents(String speakerun){
        ArrayList<Event> eventlist = new ArrayList<Event>();
        Map<String, Event> allevents = EventManager.getEventList();
        for(String event : UserAccount.unToSpeaker.get(speakerun).getSpeakingAt()) {
            if(allevents.get(event)!=null) {
                eventlist.add(allevents.get(event));
            }
        }

        return eventlist;
    }



    /**
     * Calls the message event attendees method in speaker account
     * @param event the event name
     * @param text the message the speaker wishes to send
     * String which is the confirmation message that the message has been sent
     */
    public void callMessageEventAttendees( String event, String text){
        Message message = speakeraccount.createMessage(text);
        speakeraccount.messageEventAttendees(this.username, EventManager.EventList.get(event).getName(), message.getId());
        commonPrintsPresenter.printMessageSent();
    }

    /**
     * Calls the send to method from speaker account
     * @param to the username of the user this speaker is sending a message to
     * @param text the text of the message being sent
     * returns a print statement stating either that the message was sent successfully or not
     */
    public void callSendTo(String to, String text){
        Message message = speakeraccount.createMessage(text);
        if (!UserAccount.unToSpeaker.containsKey(to)){
            commonprints.printUserNotFound();
        }
        else {
            speakeraccount.sendTo(this.username, to, message.getId());
            commonprints.printMessageSent();
        }
    }

    /**
     * Calls the view messages method from speaker account and returns a
     * @param u2 the username of the user whose messages this speaker would like to view
     */
    public void callViewMessages(String u2){
        if (speakeraccount.viewMessages(this.username, u2).isEmpty()){
            commonprints.printEmptyMessages();
        }
        else {
            commonprints.viewMessages(speakeraccount.viewMessages(this.username, u2));
        }
    }

    public Map<String, ArrayList<String>> getAllMessages(){
        return speakeraccount.viewAllMessages(this.username);
    }

    public String getUsername(){return username;}

}