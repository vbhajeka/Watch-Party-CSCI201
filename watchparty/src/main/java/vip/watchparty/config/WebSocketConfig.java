package vip.watchparty.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


//Config for Spring STOMP messaging over websocket

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //Create websocket server at a specified endpoint, enable sockJS
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
    }



    //Configures where messages are sent
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //Create in memory message broker with destination PREFIXES
        //for sending messages. Pub-sub architecture
        registry.enableSimpleBroker("/syncplayer", "/chat");

        //Sets app as the prefix for all messages bound for message mappings
        //Our message controller is given uri /app/controller
        registry.setApplicationDestinationPrefixes("/app");


    }
}
