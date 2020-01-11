package com.na.backend.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.na.backend.exception.InvalidProviderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class OAuthManager {

    public static String getUid(String provider, String accessToken) {
        String userId="";

        switch(provider) {
            case "kakao":
                String meURL = "https://kapi.kakao.com/v2/user/me";
                try{
                    URL url = new URL(meURL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Authorization", "Bearer "+accessToken);
                    conn.setRequestProperty("Content-type","application/x-www-form-urlencoded;charset=utf-8");

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

                    userId = element.getAsJsonObject().get("id").toString();
                }catch (IOException e) {
                    e.printStackTrace();
                }
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

                    userId = data.getAsJsonObject().get("user_id").toString();
                }catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                throw new InvalidProviderException();
        }

        return userId;
    }

}
