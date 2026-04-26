package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SvgToCssUtilTest {

    @Test
    public void testConvertToDataUri() {
        String svg = "<svg></svg>";
        String result = SvgToCssUtil.convertToDataUri(svg);
        assertTrue(result.startsWith("url(\"data:image/svg+xml;base64,"));
        assertTrue(result.endsWith("\")"));
    }

    @Test
    public void testConvertToBackgroundImage() {
        String svg = "<svg></svg>";
        String result = SvgToCssUtil.convertToBackgroundImage(svg);
        assertTrue(result.contains("background-image:"));
    }

    @Test
    public void testEmptyInput() {
        assertEquals("", SvgToCssUtil.convertToDataUri(""));
    }
}
