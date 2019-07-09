package com.chat.translate.controller;

import com.chat.translate.model.Message;
import com.chat.translate.model.MessagePackage;
import com.chat.translate.service.TranslateService;
import com.chat.translate.translator.TranslatorLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired private TranslateService translateService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/chat/public")
    public MessagePackage sendMessage(@Payload Message message){
        MessagePackage messagePackage = translateService.translateMessage(
                message.getMessage(),
                TranslatorLanguage.getByCode(message.getLang())
        );
        messagePackage.setSender(message.getSender());
        return messagePackage;
    }

}
