package com.chat.translate.controller;

import com.chat.translate.model.User;
import com.chat.translate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {

    @Autowired private UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/users/public")
    public List<User> addUser(@Payload User user, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", user.getName());
        headerAccessor.getSessionAttributes().put("language", user.getLanguage());
        userService.addUser(user);
        return userService.getUserList();
    }
}
