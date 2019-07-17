package com.chat.translate.service;

import com.chat.translate.model.Message;
import com.chat.translate.model.MessagePackage;
import com.chat.translate.translator.Translation;
import com.chat.translate.translator.TranslationClient;
import com.chat.translate.translator.Translator;
import com.chat.translate.translator.TranslatorLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TranslateService {

    private static final Logger logger = LoggerFactory.getLogger(TranslateService.class);
    private Translator translator;
    private UserService userService;

    @Autowired
    public TranslateService(@Value("${apiKey}") String apiKey, UserService userService) {
        this.translator = new Translator(new TranslationClient(apiKey));
        this.userService = userService;
    }

    public MessagePackage translateMessage(String message, TranslatorLanguage sourceLang) {
        MessagePackage messagePackage = new MessagePackage();
        for(TranslatorLanguage lang : userService.getUserLanguages()){
            if(lang == sourceLang){
                messagePackage.addMessage(new Message(lang.getLang(), message));
            } else {
                try{
                    Translation translation = translator.getTranslation(message, sourceLang, lang);
                    messagePackage.addMessage(new Message(lang.getLang(), translation.getTranslatedText()));
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return messagePackage;
    }

    public Message singleTranslation(String message, TranslatorLanguage sourceLang, TranslatorLanguage targetLang) {
        Message msg = null;
        try{
            Translation translation = translator.getTranslation(message, sourceLang, targetLang);
            msg = new Message(targetLang.getLang(), translation.getTranslatedText());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return msg;
    }

}
