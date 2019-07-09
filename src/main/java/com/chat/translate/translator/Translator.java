package com.chat.translate.translator;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Translator {

    private static final Logger logger = LoggerFactory.getLogger(Translator.class);

    private TranslationClient client;

    public Translator(TranslationClient client) {
        this.client = client;
    }

    public Translation getTranslation(String translationText, TranslatorLanguage sourceLang, TranslatorLanguage targetLang) throws IOException, NullPointerException {
        Response response = client.sendRequest(buildRequest(translationText, sourceLang.getLang(), targetLang.getLang()));
        ResponseBody body = response.body();
        if(!response.isSuccessful()) {
            logger.error(parseError(body));
        } else {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(body.string(), JsonObject.class);
            JsonObject firstTranslation = jsonObject.getAsJsonObject("data")
                    .getAsJsonArray("translations")
                    .get(0)
                    .getAsJsonObject();
            return new Translation(firstTranslation.get("translatedText").getAsString(), sourceLang, targetLang);
        }
        throw new IOException("Unable to complete translation request");
    }

    private Request buildRequest(String translationText, String sourceLang, String targetLang) {
        return new Request.Builder()
                .url(buildTargetUrl(translationText, sourceLang, targetLang))
                .method("POST", RequestBody.create(null, new byte[0]))
                .build();
    }

    private HttpUrl buildTargetUrl(String translationText, String sourceLang, String targetLang) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host(client.getBaseUrl())
                .addPathSegment("language")
                .addPathSegment("translate")
                .addPathSegment("v2")
                .addQueryParameter("q", translationText)
                .addQueryParameter("target", targetLang)
                .addQueryParameter("format", "text")
                .addQueryParameter("source", sourceLang)
                .addQueryParameter("model", "base")
                .addQueryParameter("key", client.getApiKey())
                .build();
    }

    private String parseError(ResponseBody body) {
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(body.string(), JsonObject.class);
            return jsonObject.getAsJsonObject("error").get("message").getAsString();
        } catch (IOException e){
            logger.error(e.getMessage());
        }
        return "Unable to retrieve http error message";
    }
}
