package src;

import java.io.IOException;
import java.util.*;

public class OrganizerController extends UserController{
    private SpeakerAccount sa;
    private RoomManager rm;
    private OrganizerPresenter op;
    private Scanner sc;
    private AttendeesAccount aA;
    private VIPAttendeeAccount vipA;

    /**
     * The constructor for this organizer portal.
     */
    public OrganizerController(String username) {
        super(username);
        sa = new SpeakerAccount();
        rm = new RoomManager();
        op = new OrganizerPresenter();
        sc = new Scanner(System.in);
        aA = new AttendeesAccount();
        vipA = new VIPAttendeeAccount();
    }

    /**
     * Accepts user input and calls the appropriate controller and use case
     * methods to perform the requested operation.
     * Continues to request for further operations until the Attendee
     * selects "log out".
     * Returns nothing (void).
     */
    public void chooseAction() throws IOException, ClassNotFoundException {
        int input;
        do {
            op.printActionText();
            input = getValidInput(1, 16);
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
                    op.printUserType();
                    String type = sc.nextLine();
                    op.printRequestUsername();
                    String username = sc.nextLine();
                    op.printRequestPassword();
                    String password = sc.nextLine();
                    this.createAccount(type, username, password);
                    break;

                }
                case 4: {
                    commonPrintsPresenter.printEventName();
                    String eventName = sc.nextLine();
                    op.printEventType();
                    String type = sc.nextLine();
                    op.printSpeakerName();
                    String speakerUserName = sc.nextLine();
                    String[] speakerArray = this.createSpeakerArray(speakerUserName);
                    op.printRoomName();
                    String roomName = sc.nextLine();
                    op.printDescription();
                    String description = sc.nextLine();
                    Calendar startTime = this.getTimeInput(true);
                    Calendar endTime = this.getTimeInput(false);
                    op.printEnterLimit();
                    String lim = sc.nextLine();
                    int limit = Integer.parseInt(lim);
                    this.scheduleSpeaker(eventName, speakerArray, startTime, endTime, roomName, description, type, limit);
                    break;
                }
                case 5: {
                    commonPrintsPresenter.printMessageText();
                    String message = sc.nextLine();
                    this.sendToAllSpeakers(message);
                    break;
                }
                case 6:
                case 8: {
                    commonPrintsPresenter.printMessageUserName();
                    String speakerName = sc.nextLine();
                    commonPrintsPresenter.printMessageText();
                    String message = sc.nextLine();
                    this.callSendTo(speakerName, message);
                    break;
                }
                case 7: {
                    commonPrintsPresenter.printMessageText();
                    String message = sc.nextLine();
                    this.sendToAllAttendees(message);
                    break;
                }
                case 9: {
                    commonPrintsPresenter.printViewMessageUserName();
                    String un = sc.nextLine();
                    this.callViewMessages(un);
                    break;
                }
                case 10: {
                    this.viewScheduledEventsAttending(username);
                    break;
                }
                case 11: {
                    commonPrintsPresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.callSignUp(username, event_name);
                    break;
                }
                case 12: {
                    commonPrintsPresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.callDeleteEvent(username, event_name);
                    break;
                }
                case 13: {
                    this.viewAllEvents();
                    break;
                }
                case 14: {
                    commonPrintsPresenter.printEventName();
                    String event_name = sc.nextLine();
                    this.deleteEvent(event_name);
                    break;
                }
                case 15: {
                    commonPrintsPresenter.printEventName();
                    String event_name = sc.nextLine();
                    op.printEnterLimit();
                    String lim = sc.nextLine();
                    this.changeEventCapacity(event_name, lim);
                    break;
                }

            }
        } while(input != 16);
    }

    /**
     * Changes the attendee capacity of the event event_name iff the limit
     * is greater than the number of users currently signed up. Otherwise, sets
     * limit to the number of users signed up.
     * @param event_name the name of the event whose capacity must be changed
     * @param new_limit the new capacity to be set for the event
     */
    public void changeEventCapacity(String event_name, String new_limit){
        if (EventManager.EventList.containsKey(event_name)){
            Event event = EventManager.EventList.get(event_name);
            int limit = Integer.parseInt(new_limit);
            if (limit < event.getAttendee_list().size()){
                event.setLimit(event.getAttendee_list().size());
                op.printNoLimitChange();
            }
            else {
                event.setLimit(limit);
            }
        }
    }

    /**
     * Returns an array of Strings representing the usernames of the speakers
     * to be scheduled for this event. The array could be empty or contain one or more
     * speaker usernames
     * @param spNames the comma separated string containing all the speaker usernames
     * @return an array containing the speaker usernames
     */
    public String[] createSpeakerArray(String spNames){
        if (spNames.equals("null")){
            return new String[]{};
        }
        else if (spNames.contains(",")){
            return spNames.split(",");
        }
        else {
            return new String[]{spNames};
        }
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
     * Create a new Organizer account in the system.
     * @param userName the user name for this new organizer
     * @param password the password for this new organizer.
     */
    public void createNewAccount(String userName, String password) {
        oa.addOrganizer(userName, password);
        commonPrintsPresenter.printSuccessfulAccountCreation();
    }

    /**
     * Enter the room with name roomName into the system.
     * @param roomName the name of the room to be added to the system.
     */
    public void enterRoomIntoSystem(String roomName) {
        rm.addRoom(roomName);
        op.printRoomAdded();
    }

    /**
     * Remove the room with roomName from the system.
     * @param roomName the name of the room to be removed from the system
     * @return true iff the room was already in the system
     */
    public boolean deleteRoomFromSystem(String roomName) {
        if (rm.removeRoom(roomName)){
            op.printRoomDeleted();
            return true;
        }
        op.printRoomNotDeleted();
        return false;
    }

    /**
     * Void method that creates a user account (speaker, attendee or VIP attendee) depending
     * on the type passed to the method. If type is speaker, a speaker account
     * is created and so on.
     * @param type the type of user account to create (NOT organiser)
     * @param username the username of the user account to be created
     * @param password the password of the user account to be created
     */
    public void createAccount(String type, String username, String password){
        if (type.equalsIgnoreCase("Attendee")){
            aA.addAttendee(username, password);
            op.printAttendeeCreated();

        }
        else if (type.equalsIgnoreCase("VIP Attendee")){
            vipA.addVIPAttendee(username, password);
            op.printAttendeeCreated();
        }
        else if (type.equalsIgnoreCase("Speaker")){
            sa.addSpeaker(username, password);
            op.printSpeakerAdded();
        }
        else if (type.equalsIgnoreCase("Organiser")){
            oa.addOrganizer(username, password);
            op.printOrganiserCreated();
        }

    }

    /**
     * Schedule the speakers in the array speakerUserNames to speak in room with name room
     * at date and time d.
     * @param eventName the name of the talk you want to schedule.
     * @param speakerUserNames an array of usernames of the speakers you want to schedule to speak
     * @param startTime the date and time at which you want to schedule the speaker to start speaking
     * @param endTime the date and time at which you want to schedule the speaker to stop speaking
     * @param room the name of the room where you want the speaker to speak.
     * on the date and time specified
     * @param description the event description
     * @param type the type of the event you want to schedule
     * Preconditions: Start time must be before end time, the room must be in the system,
     * the speaker must have an account in the system.
     */
    public void scheduleSpeaker(String eventName, String[] speakerUserNames, Calendar startTime, Calendar endTime,
                                String room, String description, String type, int limit) {
        boolean cont = true;

        if (!rm.doesRoomExist(room)){
            op.printRoomDNE();
        }
        else if (eventManager.isConflicting(rm.getEvents(room), startTime, endTime)){
            op.printEventClash();
        }
        else if (speakerUserNames.length != 0) {
            for (String speaker : speakerUserNames) {
                if (!sa.speakerExists(speaker)) {
                    op.printSpeakerDNE();
                    cont = false;
                    break;
                }
                if (eventManager.isConflicting(sa.getEvents(speaker), startTime, endTime)) {
                    op.printEventClash();
                    cont = false;
                    break;
                }
            }
        }
        if (cont){
            eventManager.createEvent(eventName, room, speakerUserNames, description, startTime, endTime, type, limit);
            for (String speaker: speakerUserNames){
                sa.addEventToSpeaker(speaker, eventName);
            }
            rm.addEventToRoom(room, eventName);
            op.printSuccessfullyScheduled();
        }

    }

    /**
     * Calls the send to speakers method in organizer account
     * @param text the text of the message
     * a message stating that the message has been successfully sent
     */
    public void sendToAllSpeakers(String text){
        oa.sendToSpeakers(this.username, oa.createMessage(text).getId());
        commonPrintsPresenter.printMessageSent();
    }

    /**
     * Calls the send to all attendees method in organizer account
     * @param text the text of the message being sent
     * a message stating that the message has been successfully sent
     */
    public void sendToAllAttendees(String text){
        oa.sendToAttendees(this.username, oa.createMessage(text).getId());
        commonPrintsPresenter.printMessageSent();
    }

    /**
     * calls the send to method in organizer account
     * @param to the username of the user this message is being sent to
     * @param text the text of the message being sent
     * a message stating that the message has been sent successfully
     */
    public void callSendTo(String to, String text){
        if (!UserAccount.unToSpeaker.containsKey(to) || !UserAccount.unToAttendee.containsKey(to)){
            commonPrintsPresenter.printUserNotFound();
        }
        else {
            oa.sendTo(this.username, to, oa.createMessage(text).getId());
            commonPrintsPresenter.printMessageSent();
        }
    }

    /**
     * calls the view message method in organizer account
     * @param a2 the username of the user whose messages you would live to view
     * a list of messages received by the organizer from the user a2
     */
    public void callViewMessages(String a2){
        if (!UserAccount.unToOrganizer.containsKey(a2)){
            commonPrintsPresenter.printUserNotFound();
        }
        else if (oa.viewMessages(this.username, a2).isEmpty()){
            commonPrintsPresenter.printEmptyMessages();
        }
        else {
            commonPrintsPresenter.viewMessages(oa.viewMessages(this.username, a2));
        }
    }

    /**
     * Deletes an event from the Tech Conference's system.
     * Removes even from attendees, speakers and organisers attending/speaking list
     * @param event_name the name of the event we wish to delete
     */
    public void deleteEvent(String event_name){
        if (eventManager.canDeleteEvent(event_name)){
            Event event = EventManager.EventList.get(event_name);
            for (String att: event.getAttendee_list()){
                callDeleteEvent(att, event_name);
            }
            for (String speakers: event.getSpeaker()){
                Speaker sp = SpeakerAccount.unToSpeaker.get(speakers);
                sp.removeSpeaking(event_name);
            }
            EventManager.EventList.remove(event_name);
        }
    }

}