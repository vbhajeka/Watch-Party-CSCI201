package vip.watchparty.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vip.watchparty.persistence.models.Message;
import vip.watchparty.persistence.models.OutputMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MessageController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(Message message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());

        return new OutputMessage(message.getFrom(), message.getText(), time);
    }

}
