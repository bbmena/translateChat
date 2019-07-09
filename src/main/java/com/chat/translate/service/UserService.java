package com.chat.translate.service;

import com.chat.translate.WebSocketEventListener;
import com.chat.translate.model.User;

import com.chat.translate.translator.TranslatorLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private static Map<String, User> userMap = new HashMap<>();
    private static Map<TranslatorLanguage, Integer> userLanguages = new HashMap<>();

    public List<User> getUserList() {
        return new ArrayList(userMap.values());
    }

    public void addUser(User user) {
        userMap.put(user.getName(), user);
        addUserLanguage(user);
        logger.info("User added: " + user.toString());
    }

    public void removeUser(String userName) {
        if(userMap.containsKey(userName)){
            removeUserLanguage(userMap.get(userName));
        }
        logger.info("Removing User: " + userMap.get(userName).toString());
        userMap.remove(userName);
    }

    public List<TranslatorLanguage> getUserLanguages(){
        return new ArrayList<>(userLanguages.keySet());
    }

    private void addUserLanguage(User user) {
        TranslatorLanguage userLang = TranslatorLanguage.getByCode(user.getLanguage());
        if(userLanguages.containsKey(userLang)){
            userLanguages.put(userLang, userLanguages.get(userLang)+1);
        } else {
            userLanguages.put(userLang, 1);
        }
    }

    private void removeUserLanguage(User user) {
        TranslatorLanguage userLang = TranslatorLanguage.getByCode(user.getLanguage());
        if(userLanguages.containsKey(userLang)){
            if(userLanguages.get(userLang) <= 1){
                userLanguages.remove(userLang);
            } else {
                userLanguages.put(userLang, userLanguages.get(userLang)-1);
            }
        }
    }

}
