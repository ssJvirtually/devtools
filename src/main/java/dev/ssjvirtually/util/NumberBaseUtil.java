package dev.ssjvirtually.util;

import java.math.BigInteger;

public final class NumberBaseUtil {

    private NumberBaseUtil() {
    }

    public static String convert(String input, int fromBase, int toBase) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        
        try {
            String cleanInput = input.trim();
            // Handle common prefixes if present and fromBase allows them
            if (fromBase == 16 && cleanInput.toLowerCase().startsWith("0x")) {
                cleanInput = cleanInput.substring(2);
            } else if (fromBase == 8 && cleanInput.startsWith("0") && cleanInput.length() > 1) {
                cleanInput = cleanInput.substring(1);
            } else if (fromBase == 2 && cleanInput.toLowerCase().startsWith("0b")) {
                cleanInput = cleanInput.substring(2);
            }

            BigInteger number = new BigInteger(cleanInput, fromBase);
            return number.toString(toBase).toUpperCase();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input for base " + fromBase);
        }
    }
}
