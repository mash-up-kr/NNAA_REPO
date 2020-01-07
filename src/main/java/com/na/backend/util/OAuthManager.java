package com.na.backend.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.na.backend.entity.User;
import com.na.backend.exception.InvalidIdTokenException;
import com.na.backend.exception.InvalidProviderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;

public class OAuthManager {

    public static User getUser(String provider, String accessToken) throws GeneralSecurityException, IOException {

        String nickname=null;
        String email=null;
        Long userId=null;
        Boolean emailVerified = Boolean.FALSE;

        switch(provider) {
            case "kakao":
                String meURL = "https://kapi.kakao.com/v2/user/me";
                try{
                    URL url = new URL(meURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    conn.setRequestProperty("Authorization", "Bearer "+accessToken);

                    int responseCode = conn.getResponseCode();
                    System.out.println("Response Code : "+responseCode);

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = "";
                    String result = "";

                    while((line = br.readLine()) != null) {
                        result += line;
                    }

                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(result);

                    userId = element.getAsJsonObject().get("id").getAsLong();

                    JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
                    JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

                    nickname = properties.getAsJsonObject().get("nickname").getAsString();
                    email = kakao_account.getAsJsonObject().get("email").getAsString();
                    emailVerified = kakao_account.getAsJsonObject().get("is_email_verified").getAsBoolean();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                return User.builder()
                        .uid(userId)
                        .email(email)
                        .emailVerified(emailVerified)
                        .name(nickname)
                        .build();
            case "instagram":
                break;
            case "facebook":
                String debugURL = "https://graph.facebook.com/debug_token";
                String myAccessToken = "879784569086450|ZuBrf7jBbgTPEwecRuCbSMnAm5w";
                try{
                    debugURL += "?input_token="+accessToken;
                    debugURL += "&access_token="+myAccessToken;
                    URL url = new URL(debugURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();
                    System.out.println("Response Code : "+responseCode);

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = "";
                    String result = "";

                    while((line = br.readLine()) != null) {
                        result += line;
                    }

                    JsonParser parser = new JsonParser();
                    JsonElement element = parser.parse(result);

                    JsonObject data = element.getAsJsonObject().get("data").getAsJsonObject();

                    userId = data.getAsJsonObject().get("user_id").getAsLong();
                }catch (IOException e) {
                    e.printStackTrace();
                }

                return User.builder()
                        .uid(userId)
                        .build();
        }
        throw new InvalidProviderException();
    }





}
