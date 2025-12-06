package com.back.util;

import org.jspecify.annotations.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

@Component
public class SelfPasswordEncoder implements PasswordEncoder {

    public static String generateSalt() {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int saltLength = 8;
        SecureRandom secureRandom = new SecureRandom();

        StringBuilder salt = new StringBuilder();
        for (int i = 0; i < saltLength; i++) {
            int randomIndex = secureRandom.nextInt(charSet.length());
            salt.append(charSet.charAt(randomIndex));
        }
        return salt.toString();
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            byte[] hash = md.digest((password + salt).getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException error) {
            throw new RuntimeException("Ошибка hash", error);
        }
    }

    public static boolean checkPassword(String password, String hashedPassword, String salt) {
        return hashPassword(password, salt).equals(hashedPassword);
    }

    @Override
    public @Nullable String encode(@Nullable CharSequence rawPassword) {
        return hashPassword(Objects.requireNonNull(rawPassword).toString(), "");
    }

    @Override
    public boolean matches(@Nullable CharSequence rawPassword, @Nullable String encodedPassword) {
        return hashPassword(Objects.requireNonNull(rawPassword).toString(), "").equals(encodedPassword);
    }
}