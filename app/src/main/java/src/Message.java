package src;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class Message implements Serializable {


    private String text;
    private String date;
    private String id;

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
        return text + "\t\t" + date;
    }

    /**
     * A method that gives the id of a message
     * @return Returns the UUID string of this message
     */
    public String getId(){ return this.id; }

}

