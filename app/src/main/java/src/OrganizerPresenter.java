package src;

public class OrganizerPresenter {

    /**
     * The OrganizerPresenter constructor.
     */
    public OrganizerPresenter(){}

    /**
     * Print the menu to the user.
     */
    public void printActionText(){
        System.out.println("What would you like to do? Please enter the corresponding number: \n" +
                "1. Enter a room into the system \n" +
                "2. Delete a room from the system \n" +
                "3. Create a speaker, attendee, VIP attendee or organiser account \n" +
                "4. Schedule a talk \n" +
                "5. Message all speakers\n" +
                "6. Message a particular speaker \n" +
                "7. Message all attendees \n" +
                "8. Message one particular attendee \n" +
                "9. Archive/Unarchive a message.\n" +
                "10. Mark a message as unread/read.\n" +
                "11. Delete a message.\n" +
                "12. View messages sent by a particular user\n" +
                "13. View the list of scheduled events \n" +
                "14. Sign up for an event\n" +
                "15. Delete an event from your schedule\n" +
                "16. View all events in the system\n" +
                "17. Delete an event from the system\n" +
                "18. Change the capacity of a pre-existing event\n" +
                "19. Log out");
    }

    /**
     * Print if speaker does not exist.
     */
    public void printSpeakerDNE(){
        System.out.println("Speaker does not exist.");
    }

    /**
     * Print if room does not exist.
     */
    public void printRoomDNE(){
        System.out.println("This room does not exist. Please add it to the system.");
    }

    /**
     * Print if event clashes with another event.
     */
    public void printEventClash(){
        System.out.println("Event time has a conflict with another event.");
    }

    /**
     * Print if event is successfully scheduled.
     */
    public void printSuccessfullyScheduled(){
        System.out.println("Event has been scheduled.");
    }

    /**
     * Print the message if speaker has been added.
     */
    public void printSpeakerAdded(){
        System.out.println("Speaker has been added.");
    }

    /**
     * Prints the message stating that the room has been created.
     */
    public void printRoomAdded() { System.out.println("Room has been added.");}

    /**
     * Prints the message stating that the room has been deleted.
     */
    public void printRoomDeleted() { System.out.println("Room has been deleted.");}

    /**
     * Prints the message stating that the room could not be deleted.
     */
    public void printRoomNotDeleted() { System.out.println("Room could not be deleted.");}

    /**
     * Prints the message asking for a room name for room creation.
     */
    public void printRoomAddName() {
        System.out.println("Please enter the name of the room you want to add to the system:");
    }

    /**
     * Prints the message asking for a room name for room deletion.
     */
    public void printRoomDeleteName() {
        System.out.println("Please enter the name of the room you want to remove from the system:");
    }

    /**
     * Prints the message asking for a speaker name.
     */
    public void printSpeakerName() {
        System.out.println("Please enter the usernames of the speakers separated by a comma and no spaces if " +
                "there are multiple. If there are none, enter \"null\":");
    }

    /**
     * Prints the message asking for a password for the new speaker.
     */
    public void printSpeakerPassword() {
        System.out.println("Please enter the password of this new speaker account");
    }

    /**
     * Prints the message asking for the description of the event.
     */
    public void printDescription() { System.out.println("Please enter a description for this event:");}


    /**
     * Prints the message asking for the name of the room of the event.
     */
    public void printRoomName() {System.out.println("Please enter the name of the " +
            "room where the event will take place:");}
    /**
     * Prints the message asking for the time of the event.
     */
    public void printTimeInput(boolean start) {
        if(start) {
            System.out.println("Please enter a valid start date and time for this event. It must be entered " +
                    "in the format YYYY-MM-DD HH:MM am/pm. Ensure that the format is correct and the date is in " +
                    "the future.");
        }
        else{
            System.out.println("Please enter a valid end date and time for this event. It must be entered " +
                    "in the format YYYY-MM-DD HH:MM am/pm. Ensure that the format is correct and the date is in the " +
                    "future.");
        }
    }

    /**
     * Prints the message asking for the type of the event
     */
    public void printEventType(){
        System.out.println("Please enter the type of event you wish to host:");
    }

    /**
     * Prints the message asking for the event's capacity
     */
    public void printEnterLimit() {
        System.out.println("Please enter the event's attendee capacity:");
    }

    /**
     * Prints the message indicating that the event's capacity could not be changed
     */
    public void printNoLimitChange(){
        System.out.println("The limit could not be changed because too many users have already signed up. " +
                "The new limit has been set to the current number of attendees to prevent more sign ups.");
    }

    /**
     * Prints the message informing the organiser that the attendee account has been created
     */
    public void printAttendeeCreated(){
        System.out.println("The attendee account has been created");
    }

    /**
     * Prints message asking for the attendee's username
     */
    public void printRequestAttendeeUsername(){
        System.out.println("Please enter the attendee's username:");
    }

    /**
     * Prints message asking for attendee's password
     */
    public void printRequestAttendeePassword(){
        System.out.println("Please enter the attendee's password:");
    }

    public void printUserType(){
        System.out.println("Please enter the type of user account you'd like to create");
    }

    public void printRequestUsername(){
        System.out.println("Please enter the username");
    }

    public void printRequestPassword(){
        System.out.println("Please enter the password");
    }

    public void printOrganiserCreated(){
        System.out.println("The organiser account was successfully created");
    }




}
