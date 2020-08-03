package vip.watchparty.controllers.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import vip.watchparty.persistence.models.ChatMessage;
import vip.watchparty.persistence.models.PlayerEvent;

import static java.lang.String.format;

@Controller
public class RoomSocketController {

    //Create a logger
    private static final Logger logger = LoggerFactory.getLogger(RoomSocketController.class);

    //Add messaging template for STOMP
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    //Each message mapping handles STOMP messages for the specified endpoint


    //------Synced Player Endpoints-----
    //----------------------------------

    //Handles all player events by broadcasting to all users
    @MessageMapping("/room/{roomId}/syncevent")
    public void broadcast_player_event(@DestinationVariable String roomId,
                                       @Payload PlayerEvent playerEvent) {

        String destination = "/syncplayer/" + roomId;

        logger.info("Received event of type " + playerEvent.getEventType());

        //Publish player event to specified roomId
        messagingTemplate.convertAndSend(destination, playerEvent);
    }



    //------Text Chat Endpoints-----
    //--------------------------------

    //Handles text chat sent messages in a room
    @MessageMapping("/room/{roomId}/sendMessage")
    public void send_message(@DestinationVariable String roomId,
                                   @Payload ChatMessage chatMessage) {

        messagingTemplate.convertAndSend(format("/chat-room/%s", roomId), chatMessage);
    }


    //Handles a user join event
    @MessageMapping("/room/{roomId}/addUser")
    public ChatMessage add_user(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {

        //Add username to the websocket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        return chatMessage;

    }


}
