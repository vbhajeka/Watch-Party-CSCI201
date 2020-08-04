package vip.watchparty.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vip.watchparty.controllers.websockets.RoomSocketController;

import java.util.Optional;
import java.util.UUID;

@Controller
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);


//    @RequestMapping(value =  {"/room", "/room/{roomId}"}, method = RequestMethod.GET)
//    @ResponseBody
//    public ModelAndView get_room(@PathVariable(name="roomId", required = false) String id) {
//
//        if(id == null) {
//
//            //Generate a random UUID for redirect
//            UUID uuid = UUID.randomUUID();
//
//
//            logger.info("Redirecting a user to a new room" + uuid);
//            return new ModelAndView("redirect:/room/" + uuid);
//        }
//
//
//        ModelAndView mv = new ModelAndView("index");
//
//        return mv;
//    }

    @GetMapping("/room")
    public ModelAndView redirect_to_room() {

       //Generate a random UUID for redirect

        UUID uuid = UUID.randomUUID();


        logger.info("Redirecting a user to a new room" + uuid);
        return new ModelAndView("redirect:/room/" + uuid);

    }

    @GetMapping("/room/{roomId}")
    public ModelAndView get_room() {
        ModelAndView mv = new ModelAndView("WatchRoom");

        return mv;
    }


}
