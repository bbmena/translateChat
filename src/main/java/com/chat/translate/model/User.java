package com.chat.translate.model;

public class User {
    private String name;
    private String language;

    public User() {
    }

    public User(String name, String language) {
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "{name: " + this.name + ", language: " + this.language + "}";
    }
}
