package Entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Message implements Serializable {


    private String text;
    private String date;
    private String id;
    private boolean archived;
    private boolean read;

    /**
     * Constructor for a message
     * @param text the actual text of the message being sent
     */
    public Message(String text){
        Date dates = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.text = text;
        date = formatter.format(dates);
        id = UUID.randomUUID().toString();
        this.archived = false;
        this.read = false;

    }

    /**
     * A method that gives the text of a message
     * @return Returns the variable text
     */
    public String getText(){
        return text;
    }

    /**
     * The toString for message
     * @return returns the text of the message followed by the date sent
     */
    public String toString(){
        StringBuilder s = new StringBuilder(text + "\t\t\t" + date + "\t\t" + "Status: ");
        if(read) {
            s.append("Read, ");
        }
        else {
            s.append("Unread, ");
        }
        if(archived) {
            s.append("Archived");
        }
        else {
            s.append("Unarchived");
        }
        return s.toString();
    }

    /**
     * A method that gives the id of a message
     * @return Returns the UUID string of this message
     */
    public String getId(){ return this.id; }

    /**
     * A method that returns if this message has been archived
     * @return Return true iff this message has been archived
     */
    public boolean getArchived() {return this.archived;}

    /**
     * A method that returns if this message has been read
     * @return true iff this message has been read.
     */
    public boolean getRead() {return this.read;}

    /**
     * A method that sets this message to archived/not archived.
     * @param newArchived the value (archived/not archived) that this message is set to
     */
    public void setArchived(boolean newArchived) {this.archived = newArchived;}

    /**
     * A method that sets this message to read/unread
     * @param newRead the value (read/unread) that this message is set to
     */
    public void setRead(boolean newRead) {this.read = newRead;}

}

