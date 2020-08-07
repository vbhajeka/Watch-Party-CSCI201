package vip.watchparty.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vip.watchparty.controllers.websockets.RoomSocketController;
import vip.watchparty.persistence.models.Room;
import vip.watchparty.persistence.models.Video;

import java.util.*;

@Controller
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);
    Map<UUID, Room> allRooms = new HashMap<>();


    @GetMapping("/room")
    public ModelAndView redirect_to_room() {

       //Generate a random UUID for redirect

        UUID uuid = UUID.randomUUID();

        //Add room to list of all rooms
        if(!allRooms.containsKey(uuid)) {
            Room room = new Room();

            room.setId(uuid.toString());



            allRooms.put(uuid, room);
        }else {
            //Add the user to the list of users

        }


        logger.info("Redirecting a user to a new room" + uuid);
        return new ModelAndView("redirect:/room/" + uuid);

    }

    @GetMapping("/room/{roomId}")
    public ModelAndView get_room() {
        ModelAndView mv = new ModelAndView("WatchRoom");

        return mv;
    }


    /*
    Handles POST when user adds a video the queue
    @returns success/failure
     */
    @PostMapping("/room/{roomId}")
    @ResponseBody
    public String add_to_queue(@PathVariable String roomId, @RequestBody Video toAdd) {

        //Look up room
        Room targetRoom = null;
        try {
            //Look up the corresponding room
            targetRoom = allRooms.get(UUID.fromString(roomId));
        } catch (Exception e) {
            return "Failed to find room with id" + roomId;
        }


        //Attempt to add the video
        try {
            targetRoom.addToQueue(toAdd);
        } catch(Exception e) {
            e.printStackTrace();
            return "Failed to add the video to the queue";
        }

        //Success message
        return "Added a video with videoID " + toAdd.getId()+ " to the queue";
    }

    @GetMapping("/room/{roomId}/getroomqueue")
    @ResponseBody
    public List<Video> get_room_queue(@PathVariable String roomId) {

        //Look up the corresponding room
        Room targetRoom = null;
        try {
            targetRoom = allRooms.get(UUID.fromString(roomId));
        } catch (Exception e) {
            return null;
        }


        //Return the queue
        return targetRoom.getVideoQueue();
    }


}
