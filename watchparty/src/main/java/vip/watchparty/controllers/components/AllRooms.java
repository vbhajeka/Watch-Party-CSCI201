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

    public AllRooms() {
        Room room1 = new Room();
        room1.setId("tom-and-jerry");
        room1.setVideoId("qHX7kKWyX38");
        Room room2 = new Room();
        room2.setId("nfl");
        room2.setVideoId("bUDX3h5-uOo");
        Room room3 = new Room();
        room3.setId("csci");
        room3.setVideoId("eIrMbAQSU34");
        Room room4 = new Room();
        room4.setId("cnn");
        room4.setVideoId("tP6hR2KNVm0");

        allRooms.put(room1.getId(), room1);
        allRooms.put(room2.getId(), room2);
        allRooms.put(room3.getId(), room3);
        allRooms.put(room4.getId(), room4);

    }

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
