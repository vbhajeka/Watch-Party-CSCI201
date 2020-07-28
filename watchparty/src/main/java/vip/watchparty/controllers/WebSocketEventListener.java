package vip.watchparty.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import vip.watchparty.persistence.models.ChatMessage;

@Component
public class WebSocketEventListener {
    private static final Logger logger =
            LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebSockeConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        //Grab the username from the session
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User disconnected: " + username);

            //Create the user left message
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            //broadcast it to the topic
            messageTemplate.convertAndSend("/topic/public", chatMessage);

        }



    }

}
