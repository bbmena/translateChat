package com.chat.translate.model;

public class Message {
    private String lang;
    private String message;
    private String sender;

    public Message() {
    }

    public Message(String lang, String message) {
        this.lang = lang;
        this.message = message;
    }

    public Message(String lang, String message, String sender) {
        this.lang = lang;
        this.message = message;
        this.sender = sender;
    }

    public String getLang() {
        return lang;
    }
    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
}
