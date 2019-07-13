package com.chat.translate.service;

import com.chat.translate.model.Message;
import com.chat.translate.translator.TranslatorLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatHistoryService {

    @Autowired private ChatLogService chatLogService;
    @Autowired private TranslateService translateService;

    public List<Message> getMessageHistory(String userLang, int historyIndex) {
        List<Message> messages = chatLogService.getChats(historyIndex);
        for(Message msg : messages){
            if(!msg.getLang().equalsIgnoreCase(userLang)){
                Message translation = translateService.singleTranslation(
                        msg.getMessage(),
                        TranslatorLanguage.getByCode(msg.getLang()),
                        TranslatorLanguage.getByCode(userLang)
                );
                if(translation != null) {
                    msg.setMessage(translation.getMessage());
                    msg.setLang(translation.getLang());
                }
            }
        }
        return messages;
    }
}
