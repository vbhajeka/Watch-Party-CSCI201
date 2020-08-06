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
    public String add_to_queue(@RequestBody Video toAdd) {

        logger.info(toAdd.getId());

        return "Added a video to the queue";
    }


}
