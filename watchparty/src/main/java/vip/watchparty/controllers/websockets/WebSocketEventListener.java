package vip.watchparty.controllers.websockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import vip.watchparty.controllers.components.AllRooms;
import vip.watchparty.controllers.components.SharedTest;
import vip.watchparty.persistence.models.PlayerEvent;

@Component
public class WebSocketEventListener {
    private static final Logger logger =
            LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messageTemplate;

    @Autowired
    SharedTest sharedTest;

    @Autowired
    AllRooms allRooms;

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        SimpMessageHeaderAccessor headers =
                SimpMessageHeaderAccessor.wrap(event.getMessage());

        logger.info("Received a new web socket connection from " + headers.getSessionId());

    }
    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers =
                SimpMessageHeaderAccessor.wrap(event.getMessage());

        String destination = headers.getDestination();

        //Create USERJOINED PlayerEvent to send out

        PlayerEvent userJoined = new PlayerEvent();
        userJoined.setEventType(PlayerEvent.EventType.USERJOIN);

    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        logger.info("User disconnected");
    }

}
