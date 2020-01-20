package com.na.backend.util;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

public class EncryptManager {

    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16]; // 128bits
        secureRandom.nextBytes(salt);
        return Hex.encodeHexString(salt);
    }

    public static String encryptPlainString(String plainString, String salt) {
        String encryptString = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(salt.getBytes());
            byte[] digest = messageDigest.digest(plainString.getBytes());
            encryptString = Hex.encodeHexString(digest);
        } catch(NoSuchAlgorithmException e) {
            System.out.println("문자열 암호화 오류 : "+ e.getMessage());
        }

        return encryptString;
    }

    public static String createToken(String userValue, LocalDateTime currentTime, String salt) {

        return encryptPlainString(userValue + currentTime.toString(), salt);
    }
}
