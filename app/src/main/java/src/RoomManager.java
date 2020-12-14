package src;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomManager {
    public static Map<String, Room> nameToRoom; //map of rooms with the room name as the key and the room as the value.
    public RoomManager(){nameToRoom = new HashMap<>();}

    /**
     * Check if the room with name roomName exists in the map of rooms
     * @param roomName the name of the room you want to check
     * @return true iff this room name exists in the map of rooms.
     */
    public boolean doesRoomExist(String roomName) {
        return nameToRoom.containsKey(roomName);
    }
    /**
     * Enter a new room with the name roomName into the map of rooms.
     * @param roomName the name of the room to be added
     */
    public void addRoom(String roomName) {
        Room rm = new Room(roomName);
        nameToRoom.put(roomName, rm);
    }


    /**
     * Remove the key roomName from the map of rooms, if it is present.
     * @param roomName the name of the room you want to remove from the map of rooms
     * @return Return true iff the room with name roomName was in the map of rooms
     */
    public boolean removeRoom(String roomName) {
        if (nameToRoom.containsKey(roomName)) {
            nameToRoom.remove(roomName);
            return true;
        }
        return false;
    }

    /**
     * Return a the list of event names corresponding to the events scheduled in the room
     * with name roomName
     * @param roomName the name of the room whose events we are returning
     * @return the list of events scheduled in the room with name roomName
     */
    public ArrayList<String> getEvents(String roomName) {
        return nameToRoom.get(roomName).getEventsInRoom();
    }

    /**
     * Add the event with name eventName to this room's list of events.
     * @param room the name of the room to schedule the event to
     * @param eventName the name of the event to be scheduled.
     */
    public void addEventToRoom(String room, String eventName) {
        nameToRoom.get(room).addEventToRoom(eventName);
    }

    public void setCapacity(String room, int capacity){
        nameToRoom.get(room).setCapacity(capacity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void makeRoom(long capacity, ArrayList<String> eventsinRoom, String name ){
        Room new_room = new Room(name);
        eventsinRoom.forEach(new_room::addEventToRoom);
        new_room.setCapacity(Integer.parseInt(String.valueOf(capacity)));
        RoomManager.nameToRoom.put(name, new_room);
        System.out.println(nameToRoom);
    }
}
