package com.october.to.finish.restaurantwebapp.security;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptionUtil {
    private PasswordEncryptionUtil() {
    }

    public static String getEncrypted(String input) {
        return bytesToHex(encrypt(input.toCharArray()));
    }

    private static byte[] encrypt(char[] input) {
        byte[] bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(input)).array();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        return messageDigest.digest(bytes);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
