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

    /**
     * Constructor for the SerializerGateway
     */
    SerializerGateway(){
        UA = new UserAccount();
        EM = new EventManager();
    }

    /**
     * Reads user objects from the file "path" and saves it to an ArrayList
     * @param path the filepath of the file you want to read from
     * @return an ArrayList of users read from the file
     * @throws IOException failure of input/output operations i.e. file not found
     * @throws ClassNotFoundException class not found
     */
    public ArrayList<User> readFromUN(String path) throws IOException, ClassNotFoundException {
        ArrayList<User> array = new ArrayList<>();
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
        Object obj;
        while (true) {
            try{
            obj = inputStream.readObject();
            array.add((User) obj);
            }
            catch(EOFException ex){
                break;
            }
        }
        inputStream.close();
        return array;
    }

    /**
     * Reads event objects from the file "path" and saves it to an ArrayList
     * @param path the filepath of the file we want to read from
     * @return an ArrayList of event objects
     * @throws IOException file not found
     * @throws ClassNotFoundException class not found
     */
    public ArrayList<Event> readFromEvent(String path) throws IOException, ClassNotFoundException {
        ArrayList<Event> array = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
            Object obj;
            while (true) {
                try {
                    obj = inputStream.readObject();
                    array.add((Event) obj);
                } catch (EOFException e) {
                    break;
                }
            }
            inputStream.close();
        } catch (EOFException e) {}
        return array;
    }

    /**
     * Reads message objects from the file "path" and saves it to an ArrayList
     * @param path the filepath of the file to read from
     * @return an ArrayList of Message objects
     * @throws IOException file not found
     * @throws ClassNotFoundException class not found
     */
    public ArrayList<Message> readFromMessages(String path) throws IOException, ClassNotFoundException {
        ArrayList<Message> array = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(path));
            Object obj;
            while (true) {
                try {
                    obj = inputStream.readObject();
                    array.add((Message) obj);
                } catch (EOFException ex) {
                    break;
                }
            }
            inputStream.close();
        } catch(EOFException e) {}
        return array;
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
        else{
            for (Map.Entry<String,Attendee> entry : UserAccount.unToAttendee.entrySet()){
                output.writeObject(entry.getValue());
            }
            for (Map.Entry<String,Organizer> entry : UserAccount.unToOrganizer.entrySet()){
                output.writeObject(entry.getValue());
            }
            for (Map.Entry<String,Speaker> entry : UserAccount.unToSpeaker.entrySet()){
                output.writeObject(entry.getValue());
            }

        }


        output.close();
    }
}
