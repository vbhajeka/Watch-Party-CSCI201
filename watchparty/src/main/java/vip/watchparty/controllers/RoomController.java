package vip.watchparty.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vip.watchparty.controllers.components.AllRooms;
import vip.watchparty.controllers.components.SharedTest;
import vip.watchparty.persistence.models.Room;
import vip.watchparty.persistence.models.Video;
import vip.watchparty.persistence.models.Votes;

import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    AllRooms allRooms;

    @GetMapping("/room")
    public ModelAndView redirect_to_room() {

       //Generate a random UUID for redirect

        UUID uuid = UUID.randomUUID();

        //Only one thread should access allRooms at any time
        //force acquisition of monitor on singleton
        synchronized(allRooms) {
            //Add room to list of all rooms
            if(!allRooms.room_exists(uuid.toString())) {

                allRooms.create_room(uuid.toString());

            }
        }



        logger.info("Redirecting a user to a new room" + uuid);
        return new ModelAndView("redirect:/room/" + uuid);

    }

    @GetMapping("/room/{roomId}")
    public ModelAndView go_to_room(@PathVariable String roomId) {
        ModelAndView mv = new ModelAndView("WatchRoom");


        synchronized(allRooms) {

            //If the room doesnt exist this means this is an illegal
            //private room request
            //redirect to home
            if(!allRooms.room_exists(roomId.toString())) {

                return new ModelAndView("redirect:/");
            }

        }

        //Pass the current video being played in a room to the view
        String currentVideoId = get_room_video(roomId);
        mv.addObject("currentVideoId", currentVideoId);

        //Otherwise just send them to the room
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
            synchronized(allRooms) {
                targetRoom = allRooms.get_room(roomId);
            }
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


        //Decide whether to clear the voting object
        if(targetRoom.firstQueue.get()) {
            //If first queue event, clear the voting map
            targetRoom.clear_votes();
            targetRoom.firstQueue.set(false);
            targetRoom.firstVote.set(true);
        }

        //Success message
        return "Video added";
    }

    @GetMapping("/room/{roomId}/get_room_queue")
    @ResponseBody
    public List<Video> get_room_queue(@PathVariable String roomId) {

        //Look up the corresponding room
        Room targetRoom = null;
        try {

            synchronized(allRooms) {
                targetRoom = allRooms.get_room(roomId);
            }

        } catch (Exception e) {
            return null;
        }


        //Return the queue
        return targetRoom.getVideoQueue();
    }

    /*
    Post route called by private user "make private room"
    Adds a new room with unique uuid to allRooms then redirects user to that newly created room
    */
    @PostMapping("/create_private_room")
    public ModelAndView create_private_room() {
        UUID uuid = null;
        while(true) {
            uuid = UUID.randomUUID();

            synchronized(allRooms) {
                if(!allRooms.room_exists(uuid.toString())) {
                    //If room doesnt exist, uuid is valid
                    //create room and break
                    allRooms.create_room(uuid.toString());
                    break;
                }
            }
        }


        return new ModelAndView("redirect:/room/" + uuid.toString());
    }

    /*
    Called by client side javascript to get the next video to play
    after voting period ahs expired
    */

    @GetMapping("/room/{roomId}/get_vote_result")
    @ResponseBody
    public String get_vote_result(@PathVariable String roomId) {
        //Look up the room
        Room targetRoom = null;
        synchronized(allRooms) {
            try {
                targetRoom = allRooms.get_room(roomId);
            } catch(Exception e) {
                //will not throw
            }

        }

        String videoId = targetRoom.get_winning_vote();
        targetRoom.setVideoId(videoId);

        return videoId;
    }


    /*
    Route that gets post containing Javascript array of strings
    each representing a postive vote for a video

    Adds these votes to a rooms vote tally
    */
    @PostMapping("/room/{roomId}/submit_vote")
    @ResponseBody
    public String submit_vote(@PathVariable String roomId, @RequestBody Votes userVotes) {

        //Look up the room
        Room targetRoom = null;
        synchronized(allRooms) {
            try {
                targetRoom = allRooms.get_room(roomId);
            } catch(Exception e) {
                //will not throw
            }

        }

        //Add votes
        for(String video: userVotes.getVotes()) {
            targetRoom.addVote(video);
        }

        //If the first vote, clear the queue
        if(targetRoom.firstVote.get() == true) {
            targetRoom.clear_queue();
            targetRoom.firstQueue.set(true);
            targetRoom.firstVote.set(false);
        }

        return "Thanks for voting";
    }

    /*
    Called by the GetController to give the user the current video
    playing in a room
    */
    public String get_room_video(String roomId) {

        //Look up the room by uuid
        Room targetRoom = null;
        try {
            targetRoom = allRooms.get_room(roomId);
        } catch(Exception e) {
            //wont get thrown
        }

        //Return the video id
        return targetRoom.getVideoId();
    }

}

