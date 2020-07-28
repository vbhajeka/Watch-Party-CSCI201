package vip.watchparty.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS();
    }

    //
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //broker handles messages to the client on destinations prefixed
        //with /topic
        registry.enableSimpleBroker("/topic");


        //Sets app as the prefix for all messages bound for message mappings
        //Our message controller is given uri /app/greetin
        registry.setApplicationDestinationPrefixes("/app");
    }
}
