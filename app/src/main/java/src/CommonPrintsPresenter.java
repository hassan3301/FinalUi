package src;

import java.util.ArrayList;

public class CommonPrintsPresenter {
    /**
     * Constructor for CommonPrintsPresenter. This class stores all the Presenter methods that are
     * common to all users and controllers.
     */
    public CommonPrintsPresenter(){}

    /**
     * void informing that an invalid action has been selected.
     */
    public void printInvalidOption(){
        System.out.println( "Please enter a valid action.");
    }

    /**
     * void displaying the welcome message.
     */
    public void printWelcomeMenu(){
        System.out.println( "Welcome to the Tech Conference System. Please choose one of the options below: \n" +
                "1. Create a new speaker account \n" +
                "2. Create a new attendee account \n" +
                "3. Create a new organizer account \n" +
                "4. Login \n" +
                "5. Quit Program \n");
    }

    public void printRequestUsername() {
        System.out.println("Please enter the username for this account: ");
    }

    /**
     *  void confirming successful login.
     */
    public void printSuccessfulLogin(){
        System.out.println("Your log in was successful");
    }

    /**
     *  void requesting password for login.
     */
    public void printRequestPassword(){
        System.out.println("Please enter the password for this account:");
    }

    /**
     *  void informing user that the login was unsuccessful.
     * Requests username again.
     */
    public void printInvalidLogin(){
        System.out.println("Invalid login credentials. Returning to main menu...");
    }

    /**
     *  void informing user that the message has been sent.
     */
    public void printMessageSent(){
        System.out.println("Your message has been sent successfully.");
    }

    /**
     *  void informing user that the message could not be sent.
     */
    public void printMessageSendingFailure(){
        System.out.println("Your message could not be sent because the user is not on your list.");
    }

    /**
     * Prints prompt to enter action
     */
    public void printEnterOption(){
        System.out.println("Enter the action you wish to perform");
    }

    /**
     * Informs user what acceptable input values are
     * @param min the lowest possible number they can enter
     * @param max the largest possible number they can enter
     */
    public void printEnterNumber(int min, int max) {
        System.out.println("Please enter an integer between " + min + " and " + max + " (inclusive):");
    }

    /**
     * Informs user that they have been logged out of the system.
     */
    public void printLoggedOut() {
        System.out.println("You have been successfully logged out! To log in, run again.");
    }

    /**
     * Prints all events in the system
     * @param allEvents the string containing all event descriptions
     */
    public void printAllEvents(String allEvents) {
        if(allEvents.length()==0){
            printNoEventsScheduled();
        } else {
            System.out.println(allEvents);
        }
    }

    /**
     * informs user that registration for an event was successful
     */
    public void printSuccessfulSignUp(){
        System.out.println( "You have been registered for the event");
    }

    /**
     * void informing that registration for an event has failed.
     */
    public void printFailedSignUp(){
        System.out.println( "You could not register for the event");
    }

    /**
     * void confirming deletion of an event from schedule.
     */
    public void printSuccessfulDeletion(){
        System.out.println( "The event has been successfully removed from your schedule");
    }

    /**
     * void informing that deletion of the event from schedule has failed.
     */
    public void printFailedDeletion(){
        System.out.println( "The event could not be removed. This event doesn't exist in your schedule. Try another");
    }

    /**
     * prints message to inform users that the account has been created
     */
    public void printSuccessfulAccountCreation() {
        System.out.println( "Your account was successfully created! Returning you to the main menu...");
    }

    /**
     *  void informing that the user has no events scheduled.
     */
    public void printNoEventsScheduled(){
        System.out.println( "You have no events scheduled.\n");
    }

    /**
     * Prints the message asking for the text of the message being sent.
     */
    public void printMessageText() {
        System.out.println("Enter the message you would like to send:");
    }

    /**
     * Prints the message asking for the username of the person to message.
     */
    public void printMessageUserName() {
        System.out.println("Enter the username of the person you would like to message:");
    }

    /**
     * Prints the message asking for the username of a user to see their events.
     */
    public void printViewMessageUserName(){
        System.out.println("Enter the username of the user whose messages you would like to see.");
    }

    /**
     * Prints the messages.
     */
    public void viewMessages(ArrayList<String> messages) {
        for (String message: messages) {
            System.out.println(UserAccount.idToMessage.get(message));
        }
    }

    /**
     * Prints the message asking for the name of the event.
     */
    public void printEventName() {System.out.println("Please enter the name of this event: ");}

    /**
     * Prints the message asking the user what they would like to send.
     */
    public void printSend() {System.out.println("What would you like to send? ");}

    /**
     * Prints the message when event cannot be found.
     */
    public void printEventNotFound() {System.out.println("Event not found.");}

    /**
     * Prints the message when user cannot be found.
     */
    public void printUserNotFound() {System.out.println("User not Found.");}
    /**
     * Prints the message when a user tries to view messages sent or received but there are none
     */
    public void printEmptyMessages(){
        System.out.println("There were no messages");
    }

    /**
     * Prints the message when there are no events to show
     */
    public void printEmptyEventList() {System.out.println("There are no such events.");}
}
