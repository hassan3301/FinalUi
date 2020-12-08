package src;

import java.io.*;
import java.util.logging.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;

import java.util.ArrayList;

public class SerializerGateway {
    private final EventManager EM;
    private final UserAccount UA;
    private final RoomManager RM;

    /**
     * Constructor for the SerializerGateway
     */
    SerializerGateway(){
        UA = new UserAccount();
        EM = new EventManager();
        RM = new RoomManager();
    }


    /**
     * Writes all user, event and message objects to their respective files
     * @param path the file path of the file to save to
     * @throws IOException file not found
     */
    public void WriteToFile(String path) throws IOException {
        OutputStream file = new FileOutputStream(path);
        OutputStream buffer = new BufferedOutputStream(file);
        ObjectOutput output = new ObjectOutputStream(buffer);
        if (path.contains("Event")){
            for (Map.Entry<String,Event> entry : EventManager.EventList.entrySet()){
                output.writeObject(entry.getValue());
            }
        }
        else if(path.contains("Messages")){
            for (Map.Entry<String, Message> entry : UserAccount.idToMessage.entrySet()){
                output.writeObject(entry.getValue());
            }
        }
        else if(path.contains("Room")) {
            for (Map.Entry<String, Room> entry : RoomManager.nameToRoom.entrySet()){
                output.writeObject(entry.getValue());
            }
        }
        else if(path.contains("Attendee")){
            for (Map.Entry<String,Attendee> entry : UserAccount.unToAttendee.entrySet()){
                output.writeObject(entry.getValue());
            }
        }
        else if(path.contains("VIP")) {
            for (Map.Entry<String,VIPAttendee> entry : UserAccount.unToVip.entrySet()){
                output.writeObject(entry.getValue());
            }
        }
        else if(path.contains("Organizer")) {
            for (Map.Entry<String,Organizer> entry : UserAccount.unToOrganizer.entrySet()){
                output.writeObject(entry.getValue());
            }
        }
        else{
            for (Map.Entry<String,Speaker> entry : UserAccount.unToSpeaker.entrySet()){
                output.writeObject(entry.getValue());
            }

        }
        output.close();
    }

    /**
     * Reads objects of type type from the file "path" and saves it to a list
     * @param path the filepath of the file to read from
     * @return an ArrayList of objects
     * @throws IOException file not found
     * @throws ClassNotFoundException class not found
     */
    public List readFrom(String path, String type) throws IOException, ClassNotFoundException {
        List array = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
            Object obj;
            while (true) {
                try {
                    obj = inputStream.readObject();
                    if(type.equalsIgnoreCase("Organizer")) {
                        array.add((Organizer) obj);
                    }
                    else if(type.equalsIgnoreCase("Attendee")) {
                        array.add((Attendee) obj);
                    }
                    else if(type.equalsIgnoreCase("VIP Attendee")) {
                        array.add((VIPAttendee) obj);
                    }
                    else if(type.equalsIgnoreCase("Speaker")) {
                        array.add((Speaker) obj);
                    }
                    else if(type.equalsIgnoreCase("Room")) {
                        array.add((Room) obj);
                    }
                    else if(type.equalsIgnoreCase("Event")) {
                        array.add((Event) obj);
                    }
                    else {
                        array.add((Message) obj);
                    }
                } catch (EOFException ex) {
                    break;
                }
            }
            inputStream.close();
        } catch(EOFException e) {}
        return array;
    }
}
