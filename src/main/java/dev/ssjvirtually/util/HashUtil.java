package dev.ssjvirtually.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public final class HashUtil {

    private HashUtil() {
    }

    public static String hash(String input, String algorithm) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashed = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashed);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Unsupported hash algorithm: " + algorithm, ex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
