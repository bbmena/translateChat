package com.chat.translate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// two pages in UI: List of users to chat with -> click user to initialize chat
// chat page. Only chat with one user. Send languages in request to be translated by server

@SpringBootApplication
public class TranslateChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TranslateChatApplication.class, args);
    }
}
