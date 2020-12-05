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
                "3. Create a speaker account \n" +
                "4. Schedule a talk \n" +
                "5. Message all speakers\n" +
                "6. Message a particular speaker \n" +
                "7. Message all attendees \n" +
                "8. Message one particular attendee \n" +
                "9. View messages sent by a particular user\n" +
                "10. View the list of scheduled events \n" +
                "11. Sign up for an event\n" +
                "12. Delete an event from your schedule\n" +
                "13. Log out");
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
        System.out.println("Please enter the username of the speaker:");
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



}
