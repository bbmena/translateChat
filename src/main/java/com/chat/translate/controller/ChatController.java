package com.chat.translate.controller;

import com.chat.translate.model.Message;
import com.chat.translate.model.MessagePackage;
import com.chat.translate.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired private ChatService chatService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/chat/public")
    public MessagePackage sendMessage(@Payload Message message){
        return chatService.processMessage(message);
    }

}
