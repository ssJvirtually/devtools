package dev.ssjvirtually.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class SvgToCssUtil {

    private SvgToCssUtil() {
    }

    public static String convertToDataUri(String svg) {
        if (svg == null || svg.trim().isEmpty()) {
            return "";
        }

        String encoded = Base64.getEncoder().encodeToString(svg.getBytes(StandardCharsets.UTF_8));
        return "url(\"data:image/svg+xml;base64," + encoded + "\")";
    }

    public static String convertToBackgroundImage(String svg) {
        if (svg == null || svg.trim().isEmpty()) {
            return "";
        }

        return "background-image: " + convertToDataUri(svg) + ";";
    }

    public static String convertToMaskImage(String svg) {
        if (svg == null || svg.trim().isEmpty()) {
            return "";
        }

        return "mask-image: " + convertToDataUri(svg) + ";\n" +
               "-webkit-mask-image: " + convertToDataUri(svg) + ";";
    }
}
