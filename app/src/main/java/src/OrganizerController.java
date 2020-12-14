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
    TimeClass timeClass = new TimeClass();

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
                timeClass.getTime(input, time);

            }
            catch (InputMismatchException | NumberFormatException | IndexOutOfBoundsException e) {
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

    public void setRoomCapacity(String RoomName, String capacity){
        rm.setCapacity(RoomName, Integer.parseInt(capacity));
    }

    /**
     * Remove the room with roomName from the system.
     * @param roomName the name of the room to be removed from the system
     * @return true iff the room was already in the system
     */
    public boolean deleteRoomFromSystem(String roomName) {
        if (rm.removeRoom(roomName)){
            op.printRoomDeleted();
            FirebaseGateway.DeleteFromDB(roomName, "Room");
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
    public boolean scheduleSpeaker(String eventName, ArrayList<String> speakerUserNames, Calendar startTime, Calendar endTime,
                                String room, String description, String type, int limit) {
        boolean cont = true;

        if (!rm.doesRoomExist(room)){
            op.printRoomDNE();
            return false;
        }
        else if (eventManager.isConflicting(rm.getEvents(room), startTime, endTime)){
            op.printEventClash();
            return false;
        }
        else if (speakerUserNames.size() != 0) {
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
            return true;
        }
        return false;

    }

    /**
     * Adds speaker to list of speakers for the specified event.
     * @param eventname event speaker is to be added to
     * @param speakerun username of speaker to be added
     * @return true if speaker was successfully added
     */
    public boolean addSpeakerToEvent(String eventname, String speakerun){
        boolean cont = true;

        if (!sa.speakerExists(speakerun)) {
            op.printSpeakerDNE();
            cont = false;
        }
        if (eventManager.isConflicting(sa.getEvents(speakerun), eventManager.getStartTime(eventname), eventManager.getEndTime(eventname))) {
            op.printEventClash();
            cont = false;
        }

        if (cont){
            eventManager.addSpeaker(eventname, speakerun);
            sa.addEventToSpeaker(speakerun, eventname);
            op.printSuccessfullyScheduled();
            return true;
        }
        return false;

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
     * calls the send to all organizers method in organizer account
     * @param text the text of the message being sent
     *  a message stating that the message has been successfully sent
     */
    public void sendToAllOrganizers(String text){
        oa.sendToOrganizers(this.username, oa.createMessage(text).getId());
        commonPrintsPresenter.printMessageSent();
    }

    /**
     * calls the send to method in organizer account
     * @param to the username of the user this message is being sent to
     * @param text the text of the message being sent
     * a message stating that the message has been sent successfully
     */
    public void callSendTo(String to, String text){
        if (!UserAccount.unToSpeaker.containsKey(to) && !UserAccount.unToAttendee.containsKey(to)){
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

    public static ArrayList<Room> viewAllRooms(){
        Map<String, Room> allrooms = RoomManager.nameToRoom;
        return new ArrayList<Room>(allrooms.values());
    }

    public static ArrayList<Event> viewAllEventsArrayList(){
        Map<String, Event> allevents = EventManager.getEventList();
        return new ArrayList<Event>(allevents.values());
    }

    public ArrayList<Event> getScheduledEvents(String un){
        ArrayList<Event> eventlist = new ArrayList<Event>();
        Map<String, Event> allevents = EventManager.getEventList();
        for(String event : UserAccount.unToOrganizer.get(un).getEventsAttending()) {
            if(allevents.get(event)!=null) {
                eventlist.add(allevents.get(event));
            }
        }

        return eventlist;
    }

}