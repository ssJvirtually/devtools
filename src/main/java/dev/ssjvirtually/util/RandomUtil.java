package dev.ssjvirtually.util;

import java.security.SecureRandom;

public final class RandomUtil {

    private static final String ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]{}|;:,.<>?";
    
    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomUtil() {
    }

    public static String generateRandomString(int length, boolean useUpper, boolean useLower, boolean useDigits, boolean useSymbols) {
        if (length <= 0) return "";
        
        StringBuilder charset = new StringBuilder();
        if (useLower) charset.append(ALPHABET_LOWER);
        if (useUpper) charset.append(ALPHABET_UPPER);
        if (useDigits) charset.append(DIGITS);
        if (useSymbols) charset.append(SYMBOLS);
        
        if (charset.isEmpty()) {
            throw new IllegalArgumentException("At least one character set must be selected");
        }
        
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append(charset.charAt(RANDOM.nextInt(charset.length())));
        }
        
        return result.toString();
    }
}
