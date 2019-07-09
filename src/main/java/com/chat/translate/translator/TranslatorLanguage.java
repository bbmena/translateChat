package com.chat.translate.translator;

import java.util.HashMap;
import java.util.Map;

public enum TranslatorLanguage {

    ENGLISH ("en"),
    SPANISH ("es"),
    FRENCH ("fr"),
    GERMAN ("de"),
    JAPANESE ("ja"),
    NONE ("none");

    private String lang;
    private static final Map<String, TranslatorLanguage> lookup = new HashMap<String, TranslatorLanguage>();

    static {
        for (TranslatorLanguage lang : TranslatorLanguage.values()){
            lookup.put(lang.getLang(), lang);
        }
    }

    TranslatorLanguage(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public static TranslatorLanguage getByCode(String langCode){
        return lookup.get(langCode);
    }
}
