package com.chat.translate.service;

import com.chat.translate.model.Message;
import com.chat.translate.model.MessagePackage;
import com.chat.translate.translator.TranslatorLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired private ChatLogService chatLogService;
    @Autowired private TranslateService translateService;

    public MessagePackage processMessage(Message message) {
        chatLogService.saveChat(message);
        MessagePackage messagePackage = translateService.translateMessage(
                message.getMessage(),
                TranslatorLanguage.getByCode(message.getLang())
        );
        messagePackage.setSender(message.getSender());
        return messagePackage;
    }
}
