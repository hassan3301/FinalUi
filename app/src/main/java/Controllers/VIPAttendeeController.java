package Controllers;

import java.util.ArrayList;
import java.util.Scanner;

import Presenters.AttendeePresenter;
import Presenters.CommonPrintsPresenter;
import Entities.Event;
import UseCase.EventManager;

public class VIPAttendeeController extends AttendeeController {

    private AttendeePresenter attendeePresenter;
    private CommonPrintsPresenter commonPrintsPresenter;
    private Scanner sc;
    /**
     * Constructor for a VIPAttendeeController
     *
     * @param username the username of the VIPAttendee currently logged in
     */
    public VIPAttendeeController(String username) {
        super(username);
        this.commonPrintsPresenter = new CommonPrintsPresenter();
        this.attendeePresenter = new AttendeePresenter();
        this.sc = new Scanner(System.in);
    }

    /**
     * Accepts user input and calls the appropriate controller and use case
     * methods to perform the requested operation.
     * Continues to request for further operations until the Attendee
     * selects "log out".
     * Returns nothing (void).
     */
    @Override
    public void chooseAction() {
        int input;
        do {
            attendeePresenter.chooseActionTextVIP();
            input = getValidInput(1, 13);
            sc.nextLine();
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
                case 8:{
                    attendeePresenter.printAddMessagable();
                    String un = sc.nextLine();
                    callAddMessenger(un);
                    break;
                }
                case 9: {
                    String idToChange = markMessageHelper(vip.viewAllMessages(this.username),
                            "archived");
                    if (!idToChange.equals("")) {
                        archiveMessage(idToChange);
                    }
                    break;
                }
                case 10: {
                    String idToChange = markMessageHelper(vip.viewAllMessages(this.username),
                            "read");
                    if (!idToChange.equals("")) {
                        markMessageUnread(idToChange);
                    }
                    break;
                }
                case 11: {
                    String idToChange = markMessageHelper(vip.viewAllMessages(this.username),
                            "deleted");
                    if (!idToChange.equals("")) {
                        userAccount.deleteMessage(idToChange, this.username);
                        commonPrintsPresenter.printSuccessfulMessageDeletion();
                    }
                    break;
                }
                case 12:{
                    viewVIPEvents();
                    break;
                }
            }
        }while (input != 13);
    }

    /**
     * Void method that prints all the VIP only events in the system
     */
    public ArrayList<Event> viewVIPEvents(){

        ArrayList<Event> vip_events = new ArrayList<Event>();
        for (Event event: EventManager.EventList.values()){
            if (event.getType().equals("VIP")){
                vip_events.add(event);
            }
        }
        if (vip_events.size() == 0){
            commonPrintsPresenter.printEmptyEventList();
            return null;
        }
        else{
            commonPrintsPresenter.printAllEvents(vip_events.toString());
            return vip_events;
        }

    }


}
