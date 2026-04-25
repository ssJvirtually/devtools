package dev.ssjvirtually.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

public final class HtmlBeautifierUtil {

    private HtmlBeautifierUtil() {
    }

    public static String beautify(String html) {
        if (html == null || html.trim().isEmpty()) {
            return "";
        }
        Document doc = Jsoup.parse(html);
        doc.outputSettings()
                .indentAmount(4)
                .prettyPrint(true)
                .escapeMode(Entities.EscapeMode.xhtml);
        return doc.html();
    }

    public static String minify(String html) {
        if (html == null || html.trim().isEmpty()) {
            return "";
        }
        Document doc = Jsoup.parse(html);
        doc.outputSettings()
                .prettyPrint(false)
                .indentAmount(0);
        String minified = doc.html();
        // Basic minification: remove newlines and extra spaces between tags
        return minified.replaceAll(">\\s+<", "><").replaceAll("\\n", "").trim();
    }
}
