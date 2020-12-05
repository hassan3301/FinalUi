package src;

import java.io.IOException;
import java.util.*;

public class OrganizerController {
    private SpeakerAccount sa;
    private OrganizerAccount oa;
    private EventManager em;
    private RoomManager rm;
    private String userName;
    private OrganizerPresenter op;
    private CommonPrintsPresenter cpp;
    private Scanner sc;

    /**
     * The constructor for this organizer portal.
     */
    public OrganizerController(String username) {
        sa = new SpeakerAccount();
        oa = new OrganizerAccount();
        em = new EventManager();
        rm = new RoomManager();
        op = new OrganizerPresenter();
        cpp = new CommonPrintsPresenter();
        sc = new Scanner(System.in);
        this.userName = username;
    }

    /**
     * Accepts user input and calls the appropriate controller and use case
     * methods to perform the requested operation.
     * Continues to request for further operations until the Attendee
     * selects "log out".
     * Returns nothing (void).
     */
    public void chooseAction() throws IOException, ClassNotFoundException {
        int input = 0;
        do {
            op.printActionText();
            input = getValidInput(1, 13);
            sc.nextLine();
            switch (input){
                case 1: {
                    op.printRoomAddName();
                    String room = sc.nextLine();
                    this.enterRoomIntoSystem(room);
                    break;
                }
                case 2: {
                    op.printRoomDeleteName();
                    String room = sc.nextLine();
                    this.deleteRoomFromSystem(room);
                    break;
                }
                case 3: {
                    op.printSpeakerName();
                    String speakerName = sc.nextLine();
                    op.printSpeakerPassword();
                    String password = sc.nextLine();
                    this.createSpeakerAccount(speakerName, password);
                    break;
                }
                case 4: {
                    cpp.printEventName();
                    String eventName = sc.nextLine();
                    op.printSpeakerName();
                    String speakerUserName = sc.nextLine();
                    op.printRoomName();
                    String roomName = sc.nextLine();
                    op.printDescription();
                    String description = sc.nextLine();
                    Calendar startTime = this.getTimeInput(true);
                    Calendar endTime = this.getTimeInput(false);
                    this.scheduleSpeaker(eventName, speakerUserName, startTime, endTime, roomName, description);
                    break;
                }
                case 5: {
                    cpp.printMessageText();
                    String message = sc.nextLine();
                    this.sendToAllSpeakers(message);
                    break;
                }
                case 6:
                case 8: {
                    cpp.printMessageUserName();
                    String speakerName = sc.nextLine();
                    cpp.printMessageText();
                    String message = sc.nextLine();
                    this.callSendTo(speakerName, message);
                    break;
                }
                case 7: {
                    cpp.printMessageText();
                    String message = sc.nextLine();
                    this.sendToAllAttendees(message);
                    break;
                }
                case 9: {
                    cpp.printViewMessageUserName();
                    String un = sc.nextLine();
                    this.callViewMessages(un);
                    break;
                }
                case 10: {
                    String allEvents = "";
                    for (Event event: EventManager.EventList.values()){
                        allEvents = allEvents + event.toString() + "\n";
                    }
                    cpp.printAllEvents(allEvents);
                    break;
                }
                case 11: {
                    cpp.printEventName();
                    String event_name = sc.nextLine();
                    this.callSignUp(userName, event_name);
                    break;
                }
                case 12: {
                    cpp.printEventName();
                    String event_name = sc.nextLine();
                    this.callDeleteEvent(userName, event_name);
                    break;
                }
            }
        } while(input != 13);
    }

    /**
     * Returns an integer representing user input between min and max (inclusive)
     * @param input the input for the time
     * @param start the start of the time segment
     * @param end the end of the time segment
     * @return an integer of the time segment given in the parameters.
     */

    public int returnTimeSegment(String input, int start, int end) {
        return Integer.parseInt(input.substring(start, end));
    }


    /**
     * Returns a Calendar representing user input between min and max (inclusive)
     * @param start is used to call printTimeInput
     * @return a Calendar when time is after now.
     */
    public Calendar getTimeInput(boolean start) {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.MILLISECOND, 0);
        Calendar time = Calendar.getInstance();
        boolean done = false;
        while(!done) {
            op.printTimeInput(start);
            try {
                String input = sc.nextLine();
                time.set(returnTimeSegment(input, 0, 4),
                        returnTimeSegment(input, 5, 7) - 1,
                        returnTimeSegment(input, 8, 10));
                if(returnTimeSegment(input, 11, 13) == 12) {
                    time.set(Calendar.HOUR, 0);
                }
                else {
                    time.set(Calendar.HOUR, returnTimeSegment(input, 11, 13));
                }
                time.set(Calendar.MINUTE, returnTimeSegment(input, 14, 16));
                if (input.substring(17, 19).equals("am")) {
                    time.set(Calendar.AM_PM, Calendar.AM);
                } else {
                    time.set(Calendar.AM_PM, Calendar.PM);
                }
                time.set(Calendar.SECOND, 0);
                time.set(Calendar.MILLISECOND, 0);
            }
            catch (InputMismatchException e) {
                op.printTimeInput(start);
            }
            catch (IndexOutOfBoundsException i) {
                op.printTimeInput(start);
            }
            catch(NumberFormatException n) {
                op.printTimeInput(start);
            }
            if(time.after(now)) {
                done = true;
            }
        }
        return time;
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
                cpp.printEnterNumber(min, max);
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

    /**
     * Create a new Organizer account in the system.
     * @param userName the user name for this new organizer
     * @param password the password for this new organizer.
     */
    public void createNewAccount(String userName, String password) {
        oa.addOrganizer(userName, password);
        cpp.printSuccessfulAccountCreation();
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
        if (oa.canSignUp(username, event_name)){
            Organizer organizer = UserAccount.unToOrganizer.get(username);
            organizer.addEvent(event_name);
            em.update_attendees(EventManager.EventList.get(event_name), organizer);
            done = true;
        }
        else{
            done = false;
        }
        if (done){
            cpp.printSuccessfulSignUp();
        }
        else{
            cpp.printFailedSignUp();
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
        if (oa.canDeleteEvent(username, event_name)){
            Organizer organizer = UserAccount.unToOrganizer.get(username);
            organizer.getEvents().remove(event_name);
            EventManager.EventList.get(event_name).getAttendee_list().remove(organizer.getUsername());
            done = true;
        }
        else {
            done = false;
        }
        if (done){
            cpp.printSuccessfulDeletion();
        }
        else{
            cpp.printFailedDeletion();
        }
    }
    /**
     * Enter the room with name roomName into the system.
     * @param roomName the name of the room to be added to the system.
     */
    public void enterRoomIntoSystem(String roomName) {
        rm.addRoom(roomName);
    }

    /**
     * Remove the room with roomName from the system.
     * @param roomName the name of the room to be removed from the system
     * @return true iff the room was already in the system
     */
    public boolean deleteRoomFromSystem(String roomName) {
        return rm.removeRoom(roomName);
    }

    /**
     * Create a new speaker account and add it into the system.
     * @param userName the username for this speaker.
     * @param password the password for this speaker.
     */
    public void createSpeakerAccount(String userName, String password) {
        sa.addSpeaker(userName, password);
        op.printSpeakerAdded();
    }

    /**
     * Schedule the speaker with username speakerUserName to speak in room with name room
     * at date and time d.
     * @param eventName the name of the talk you want to schedule.
     * @param speakerUserName the username of the speaker you want to schedule to speak
     * @param startTime the date and time at which you want to schedule the speaker to start speaking
     * @param endTime the date and time at which you want to schedule the speaker to stop speaking
     * @param room the name of the room where you want the speaker to speak.
     * on the date and time specified
     * Preconditions: Start time must be before end time, the room must be in the system,
     * the speaker must have an account in the system.
     */
    public void scheduleSpeaker(String eventName, String speakerUserName, Calendar startTime, Calendar endTime,
                                String room, String description) {
        if (!sa.speakerExists(speakerUserName)) {
            op.printSpeakerDNE();
        }
        else if (!rm.doesRoomExist(room)) {
            op.printRoomDNE();
        }
        else {
            if(em.isConflicting(sa.getEvents(speakerUserName), startTime, endTime)
                    || em.isConflicting(rm.getEvents(room), startTime, endTime)) {
                op.printEventClash();
            }
            else {
                em.createEvent(eventName, room, speakerUserName, description, startTime, endTime);
                sa.addEventToSpeaker(speakerUserName, eventName);
                rm.addEventToRoom(room, eventName);
                op.printSuccessfullyScheduled();
            }
        }

    }

    /**
     * Calls the send to speakers method in organizer account
     * @param text the text of the message
     * a message stating that the message has been successfully sent
     */
    public void sendToAllSpeakers(String text){
        oa.sendToSpeakers(this.userName, oa.createMessage(text).getId());
        cpp.printMessageSent();
    }

    /**
     * Calls the send to all attendees method in organizer account
     * @param text the text of the message being sent
     * a message stating that the message has been successfully sent
     */
    public void sendToAllAttendees(String text){
        oa.sendToAttendees(this.userName, oa.createMessage(text).getId());
        cpp.printMessageSent();
    }

    /**
     * calls the send to method in organizer account
     * @param to the username of the user this message is being sent to
     * @param text the text of the message being sent
     * a message stating that the message has been sent successfully
     */
    public void callSendTo(String to, String text){
        oa.sendTo(this.userName, to, oa.createMessage(text).getId());
        cpp.printMessageSent();
    }

    /**
     * calls the view message method in organizer account
     * @param a2 the username of the user whose messages you would live to view
     * a list of messages received by the organizer from the user a2
     */
    public void callViewMessages(String a2){
        cpp.viewMessages(oa.viewMessages(this.userName, a2));
    }
}
