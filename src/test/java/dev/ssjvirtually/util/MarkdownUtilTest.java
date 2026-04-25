package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MarkdownUtilTest {

    @Test
    public void testToHtml() {
        String markdown = "# Title\n\n**Bold** text";
        String html = MarkdownUtil.toHtml(markdown);
        assertTrue(html.contains("<h1>Title</h1>"));
        assertTrue(html.contains("<strong>Bold</strong>"));
    }

    @Test
    public void testEmpty() {
        assertEquals("", MarkdownUtil.toHtml(""));
        assertEquals("", MarkdownUtil.toHtml(null));
    }
}
