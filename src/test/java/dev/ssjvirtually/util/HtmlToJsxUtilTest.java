package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HtmlToJsxUtilTest {

    @Test
    public void testSimpleConversion() {
        String html = "<div class=\"test\" for=\"input\">Hello</div>";
        String jsx = HtmlToJsxUtil.convertToJsx(html);
        assertEquals("<div className=\"test\" htmlFor=\"input\">Hello</div>", jsx);
    }

    @Test
    public void testStyleConversion() {
        String html = "<div style=\"color: red; background-color: blue;\">Test</div>";
        String jsx = HtmlToJsxUtil.convertToJsx(html);
        assertTrue(jsx.contains("style={{color: \"red\", backgroundColor: \"blue\"}}"));
    }

    @Test
    public void testSelfClosingTags() {
        String html = "<img src=\"test.png\"><br><input type=\"text\">";
        String jsx = HtmlToJsxUtil.convertToJsx(html);
        assertTrue(jsx.contains("<img src=\"test.png\" />"));
        assertTrue(jsx.contains("<br />"));
        assertTrue(jsx.contains("<input type=\"text\" />"));
    }

    @Test
    public void testEmptyInput() {
        assertEquals("", HtmlToJsxUtil.convertToJsx(""));
        assertEquals("", HtmlToJsxUtil.convertToJsx(null));
    }
}
