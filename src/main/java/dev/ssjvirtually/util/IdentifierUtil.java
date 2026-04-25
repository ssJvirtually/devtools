package dev.ssjvirtually.util;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

public final class IdentifierUtil {

    private static final char[] ULID_ALPHABET = "0123456789ABCDEFGHJKMNPQRSTVWXYZ".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();

    private IdentifierUtil() {
    }

    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    public static String generateUlid() {
        long time = Instant.now().toEpochMilli();
        byte[] random = new byte[10];
        RANDOM.nextBytes(random);
        return encodeUlid(time, random);
    }

    public static long decodeUlidTimestamp(String ulid) {
        String normalized = ulid == null ? "" : ulid.trim().toUpperCase();
        if (normalized.length() != 26) {
            throw new IllegalArgumentException("ULID must be 26 characters");
        }

        long value = 0L;
        for (int i = 0; i < 10; i++) {
            int idx = indexOfUlidChar(normalized.charAt(i));
            if (idx < 0) {
                throw new IllegalArgumentException("Invalid ULID character at position " + (i + 1));
            }
            value = (value << 5) | idx;
        }

        return value & 0xFFFFFFFFFFFFL;
    }

    private static String encodeUlid(long time, byte[] random) {
        if (time < 0 || time > 0xFFFFFFFFFFFFL) {
            throw new IllegalArgumentException("Time must fit in 48 bits");
        }
        if (random == null || random.length != 10) {
            throw new IllegalArgumentException("Random part must be 10 bytes");
        }

        char[] out = new char[26];
        long t = time;
        for (int i = 9; i >= 0; i--) {
            out[i] = ULID_ALPHABET[(int) (t & 31)];
            t >>>= 5;
        }

        int bitBuffer = 0;
        int bitCount = 0;
        int outIndex = 10;
        for (byte b : random) {
            bitBuffer = (bitBuffer << 8) | (b & 0xFF);
            bitCount += 8;
            while (bitCount >= 5) {
                int shift = bitCount - 5;
                int idx = (bitBuffer >> shift) & 31;
                out[outIndex++] = ULID_ALPHABET[idx];
                bitCount -= 5;
            }
        }

        if (bitCount > 0) {
            out[outIndex++] = ULID_ALPHABET[(bitBuffer << (5 - bitCount)) & 31];
        }

        return new String(out);
    }

    private static int indexOfUlidChar(char c) {
        for (int i = 0; i < ULID_ALPHABET.length; i++) {
            if (ULID_ALPHABET[i] == c) {
                return i;
            }
        }
        return -1;
    }
}
