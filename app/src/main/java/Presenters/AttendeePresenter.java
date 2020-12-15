package Presenters;

public class AttendeePresenter {

    /**
     * Constructor for AttendeePresenter
     */
    public AttendeePresenter(){}

    /**
     * Prints all the possible actions an Attendee can perform
     */
    public void chooseActionText(){

        System.out.println("What would you like to do? Choose the corresponding number: \n" +
                "1. Sign Up for event \n" +
                "2. Remove event from schedule\n" +
                "3. View all events\n" +
                "4. View scheduled events\n" +
                "5. Read messages received from a particular user\n" +
                "6. Read message sent to a particular user\n" +
                "7. Send a message\n" +
                "8. Add a user to your messagable list.\n" + "" +
                "9. Archive/Unarchive a message.\n" +
                "10. Mark a message as unread/read.\n" +
                "11. Delete a message.\n" +
                "12. Logout");
    }

    /**
     * Prints all possible actions for a VIP Attendee
     */
    public void chooseActionTextVIP(){
        System.out.println("What would you like to do? Choose the corresponding number: \n" +
                "1. Sign Up for event \n" +
                "2. Remove event from schedule\n" +
                "3. View all events\n" +
                "4. View scheduled events\n" +
                "5. Read messages received\n" +
                "6. Read message sent\n" +
                "7. Send a message\n" +
                "8. Add a user to your messagable list.\n" +
                "9. Archive/Unarchive a message.\n" +
                "10. Mark a message as unread/read.\n" +
                "11. Delete a message.\n" +
                "12. View all VIP only events\n" +
                "13. Log out");
    }

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

    /**
     * Prints the message asking for a speaker name.
     */
    public void printUsernameHistory() {
        System.out.println("Please enter the username of the user that you have sent messages to:");
    }

    /**
     * Prints the message asking the user what they would like to send.
     */
    public void printSend() {System.out.println("What would you like to send? ");}

    /**
     * Prints the message asking the user for the username of the user they would like to add to their messagable list.
     */
    public void printAddMessagable(){
        System.out.println("Enter the username of the user you would like to add.");
    }

    /**
     * Prints a message stating that a user has successfully been added to their messenger list
     */
    public void printSuccessfulAdd(){
        System.out.println("You can now message that user.");
    }


}
