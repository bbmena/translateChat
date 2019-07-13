package com.chat.translate.controller;

import com.chat.translate.model.Message;
import com.chat.translate.service.ChatHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping(path = "/api")
public class ChatHistoryController {

    @Autowired private ChatHistoryService chatHistoryService;

    @RequestMapping(method = RequestMethod.GET, path = "/history")
    public List<Message> getHistory(@RequestParam String userLang, @RequestParam int historyIndex) {
        return chatHistoryService.getMessageHistory(userLang, historyIndex);
    }
}
