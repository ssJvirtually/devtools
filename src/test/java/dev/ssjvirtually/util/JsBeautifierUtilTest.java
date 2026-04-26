package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsBeautifierUtilTest {

    @Test
    public void testBeautify() {
        String js = "function test(){console.log('hello');}";
        String beautified = JsBeautifierUtil.beautify(js);
        assertNotNull(beautified);
        assertTrue(beautified.contains("function test() {"));
        assertTrue(beautified.contains("  console.log(\"hello\");"));
    }

    @Test
    public void testMinify() {
        String js = "function test() { \n\n console.log('hello'); \n\n }";
        String minified = JsBeautifierUtil.minify(js);
        assertNotNull(minified);
        assertTrue(minified.contains("function test"));
    }

    @Test
    public void testInvalidJs() {
        assertThrows(IllegalArgumentException.class, () -> {
            JsBeautifierUtil.beautify("function {");
        });
    }

    @Test
    public void testEmptyInput() {
        assertEquals("", JsBeautifierUtil.beautify(""));
        assertEquals("", JsBeautifierUtil.minify(null));
    }
}
