package com.chat.translate.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChatLogEntry {

    @Id
    @GeneratedValue
    private long id;
    private String chatText;
    private String userName;
    private String language;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getChatText() {
        return chatText;
    }
    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
}
