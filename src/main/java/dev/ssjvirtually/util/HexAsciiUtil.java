package dev.ssjvirtually.util;

import java.nio.charset.StandardCharsets;

public final class HexAsciiUtil {

    private HexAsciiUtil() {
    }

    public static String asciiToHex(String ascii) {
        if (ascii == null || ascii.isEmpty()) {
            return "";
        }
        byte[] bytes = ascii.getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String hexToAscii(String hex) {
        if (hex == null || hex.isEmpty()) {
            return "";
        }

        String cleaned = hex.replaceAll("\\s+", "");
        if (cleaned.length() % 2 != 0) {
            throw new IllegalArgumentException("Hex input length must be even");
        }

        byte[] bytes = new byte[cleaned.length() / 2];
        for (int i = 0; i < cleaned.length(); i += 2) {
            int hi = Character.digit(cleaned.charAt(i), 16);
            int lo = Character.digit(cleaned.charAt(i + 1), 16);
            if (hi < 0 || lo < 0) {
                throw new IllegalArgumentException("Invalid hex character");
            }
            bytes[i / 2] = (byte) ((hi << 4) + lo);
        }

        return new String(bytes, StandardCharsets.UTF_8);
    }
}
