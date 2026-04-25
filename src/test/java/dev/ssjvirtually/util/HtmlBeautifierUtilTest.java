package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HtmlBeautifierUtilTest {

    @Test
    public void testBeautify() {
        String html = "<div><p>Hello</p></div>";
        String result = HtmlBeautifierUtil.beautify(html);
        assertTrue(result.contains("<p>Hello</p>"));
        // Jsoup adds <html>, <head>, <body> by default when parsing full documents
        assertTrue(result.contains("<html>"));
    }

    @Test
    public void testMinify() {
        String html = "<div>\n    <p>Hello</p>\n</div>";
        String result = HtmlBeautifierUtil.minify(html);
        assertFalse(result.contains("\n"));
        assertTrue(result.contains("<div><p>Hello</p></div>"));
    }
}
