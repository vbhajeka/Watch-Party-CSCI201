package vip.watchparty.controllers.components;

import org.springframework.stereotype.Component;
import vip.watchparty.persistence.models.Room;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//Global storage for active rooms
@Component
public class AllRooms {

    //roomId to Room object mapping
    private Map<String, Room> allRooms = Collections.synchronizedMap(new HashMap<>());


    //Check whether the room is active
    //synchronized since this is what is first called when a user
    // joins
    public Boolean room_exists(String roomId) {

        return allRooms.containsKey(roomId);
    }

    //Creates a new room with the given room id
    public void create_room(String roomId) {
        Room newRoom = new Room();
        newRoom.setId(roomId);

        allRooms.put(roomId, newRoom);
    }

    //Looks up room by id
    public Room get_room(String roomId) throws Exception {
        Room targetRoom = null;
        try {
            targetRoom = allRooms.get(roomId);
        }
        catch (Exception e) {
            throw new Exception();
        }

        return targetRoom;
    }

}
