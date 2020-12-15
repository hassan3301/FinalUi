package Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Room implements Serializable {
    private int capacity;
    private List<String> eventsInRoom;
    private String name;

    /**
     * This room's constructor. Initializes the room with name as stated in the parameter
     * and capacity 2.
     * The events in this room is an empty ArrayList.
     * @param name The name of this room.
     */
    public Room(String name){
        this.name = name;
        this.capacity = 2;
        eventsInRoom = new ArrayList<>();
    }

    /**
     * Set the capacity of this room to newCapacity
     * @param newCapacity the new capacity of this room
     */
    public void setCapacity(int newCapacity) { this.capacity = newCapacity; }

    /**
     * Return the capacity of this room.
     * @return Return the capacity of this room.
     */
    public int getCapacity() {return this.capacity;}

    /**
     *
     * @return Return the name of this room.
     */
    public String getName() {return this.name;}

    /**
     * Set the name of this room to newName
     * @param newName the new name of this room.
     */
    public void setName(String newName) {this.name = newName;}

    /**
     * Add a new event name to the list of event names happening in this room.
     * Precondition: The event with this event name must not occur at the same time
     * as another event in this room
     * @param eventName the name of the event to add
     */
    public void addEventToRoom (String eventName) {
        this.eventsInRoom.add(eventName);
    }

    /**
     *
     * @return Return a shallow copy of the list of the name of events happening in this room.
     */
    public ArrayList<String> getEventsInRoom() {
        ArrayList<String> eventCopy = new ArrayList<String>();
        eventCopy.addAll(eventsInRoom);
        return eventCopy;
    }


}
