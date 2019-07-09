package com.chat.translate.translator;

public class Translation {

    private final String translatedText;
    private final TranslatorLanguage sourceLang;
    private final TranslatorLanguage targetLang;

    public Translation(String translatedText, TranslatorLanguage sourceLang, TranslatorLanguage targetLang) {
        this.translatedText = translatedText;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public TranslatorLanguage getSourceLang() {
        return sourceLang;
    }

    public TranslatorLanguage getTargetLang() {
        return targetLang;
    }
}
