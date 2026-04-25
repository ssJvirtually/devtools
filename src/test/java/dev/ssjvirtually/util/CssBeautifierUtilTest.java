package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CssBeautifierUtilTest {

    @Test
    public void testBeautify() {
        String css = "body{color:red;margin:0}";
        String result = CssBeautifierUtil.beautify(css);
        assertTrue(result.contains("color:red"));
        assertTrue(result.toLowerCase().contains("body"));
    }

    @Test
    public void testMinify() {
        String css = "body {\n    color: red;\n    margin: 0;\n}";
        String result = CssBeautifierUtil.minify(css);
        assertFalse(result.contains("\n"));
        assertTrue(result.contains("body{color:red;margin:0}"));
    }
}
