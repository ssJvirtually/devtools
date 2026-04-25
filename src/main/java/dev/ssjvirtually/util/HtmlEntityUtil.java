package dev.ssjvirtually.util;

import org.apache.commons.text.StringEscapeUtils;

public final class HtmlEntityUtil {

    private HtmlEntityUtil() {
    }

    public static String encode(String input) {
        if (input == null) return null;
        return StringEscapeUtils.escapeHtml4(input);
    }

    public static String decode(String input) {
        if (input == null) return null;
        return StringEscapeUtils.unescapeHtml4(input);
    }
}
