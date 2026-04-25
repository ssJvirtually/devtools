package dev.ssjvirtually.util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class TextCodecUtil {

    private TextCodecUtil() {
    }

    public static String base64Encode(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Decode(String encodedText) {
        byte[] decoded = Base64.getDecoder().decode(encodedText);
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static String base64UrlDecode(String encodedText) {
        byte[] decoded = Base64.getUrlDecoder().decode(padBase64(encodedText));
        return new String(decoded, StandardCharsets.UTF_8);
    }

    public static String urlEncode(String plainText) {
        return URLEncoder.encode(plainText, StandardCharsets.UTF_8);
    }

    public static String urlDecode(String encodedText) {
        return URLDecoder.decode(encodedText, StandardCharsets.UTF_8);
    }

    private static String padBase64(String input) {
        int mod = input.length() % 4;
        if (mod == 0) {
            return input;
        }
        return input + "=".repeat(4 - mod);
    }
}
