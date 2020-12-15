package Presenters;

public class SpeakerPresenter {

    /**
     * The SpeakerPresenter constructor.
     */
    public SpeakerPresenter(){}

    /**
     * Returns string value displaying all the possible actions a Speaker
     * can perform
     * @return String of all options
     */
    public void chooseActionText(){

        System.out.println("What would you like to do? Choose the corresponding number: \n" +
                "1. View scheduled events\n" +
                "2. Read messages\n" +
                "3. Send a message\n" +
                "4. Send a message to all attendees of one of your talks\n" +
                "5. Send a message to all attendees of all of your talks\n"  +
                "6. Archive/Unarchive a message.\n" +
                "7. Mark a message as unread/read.\n" +
                "8. Delete a message.\n" +
                "9. Sign up for an event as an attendee\n" +
                "10. Delete an event you are attending from your schedule\n"+
                "11. View all the events in the system\n" +
                "12. View all events you are attending\n" +
                "13. Log out");
    }

    /**
     * Prints the message asking the user what they would like to send.
     */
    public void printSend() {System.out.println("What would you like to send? ");}

    /**
     * Prints the message asking for the name of the event.
     */
    public void printEventName() {System.out.println("Please enter the name of this event: ");}

    /**
     * Prints the message asking for a speaker name.
     */
    public void printUsername() {
        System.out.println("Please enter the username of the user you would like to message:");
    }

    /**
     * Prints the message asking for a speaker name.
     */
    public void printUsernameView() {
        System.out.println("Please enter the username of the user whose messages you would like to view:");
    }


}
