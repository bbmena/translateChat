package com.chat.translate.service;

import com.chat.translate.model.ChatLogEntry;
import com.chat.translate.model.Message;
import com.chat.translate.repository.ChatLogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatLogService {

    @Autowired private ChatLogEntryRepository chatLogEntryRepository;

    public List<Message> getChats(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 20, Sort.by("id").descending());
        Page<ChatLogEntry> page = chatLogEntryRepository.findAll(pageable);
        return page.getContent().stream()
                .sorted(Comparator.comparingLong(ChatLogEntry::getId))
                .map(chat -> new Message(chat.getLanguage(), chat.getChatText(), chat.getUserName()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Message saveChat(Message message) {
        ChatLogEntry savedChat = chatLogEntryRepository.save(messageToChat(message));
        return chatToMessage(savedChat);
    }

    private ChatLogEntry messageToChat(Message message){
        ChatLogEntry chat = new ChatLogEntry();
        chat.setChatText(message.getMessage());
        chat.setLanguage(message.getLang());
        chat.setUserName(message.getSender());
        return chat;
    }

    private Message chatToMessage(ChatLogEntry chat) {
        return new Message(chat.getLanguage(), chat.getChatText(), chat.getUserName());
    }
}
