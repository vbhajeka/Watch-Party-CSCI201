package vip.watchparty.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatRoomController {

    @GetMapping("/chat")
    public String chat_room() {
        return "chatroom";
    }
}
