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

    @Autowired private UserService userService;

    @Autowired
    public TranslateService(@Value("${apiKey}") String apiKey) {
        this.client = new TranslationClient(apiKey);
    }

    private static final Logger logger = LoggerFactory.getLogger(TranslateService.class);
    private TranslationClient client;
    private Translator translator;

    public MessagePackage translateMessage(String message, TranslatorLanguage sourceLang) {
        if(translator == null) {
            translator = new Translator(client);
        }
        return messagePackageBuilder(message, sourceLang);
    }

    private MessagePackage messagePackageBuilder(String message, TranslatorLanguage sourceLang){
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

}
