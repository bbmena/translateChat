package com.chat.translate.model;

import com.translate.core.TranslatorLanguage;

import java.util.HashMap;
import java.util.Map;

public class MessagePackage {

    private Map<String, Message> messages;
    private String sender;

    public MessagePackage(){
        messages = new HashMap<>();
    }

    public void addMessage(Message message) {
        messages.put(message.getLang(), message);
    }

    public Message getMessageByLang(TranslatorLanguage language){
        return messages.get(language.getLang());
    }

    public Map<String, Message> getAllMessages() {
        return messages;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
