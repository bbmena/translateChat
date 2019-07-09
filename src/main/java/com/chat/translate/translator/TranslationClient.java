package com.chat.translate.translator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class TranslationClient {

    private final String apiKey;
    private final String baseUrl = "translation.googleapis.com";
    private OkHttpClient httpClient;


    public TranslationClient(String apiKey){
        this.apiKey = apiKey;
        httpClient = new OkHttpClient();
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Response sendRequest(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }
}
