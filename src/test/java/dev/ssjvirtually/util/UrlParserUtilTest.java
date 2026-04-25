package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class UrlParserUtilTest {

    @Test
    public void testParseFullUrl() {
        Map<String, String> result = UrlParserUtil.parse("https://user:pass@www.example.com:8080/path/to/resource?query=123#fragment");
        assertEquals("https", result.get("Scheme"));
        assertEquals("www.example.com", result.get("Host"));
        assertEquals("8080", result.get("Port"));
        assertEquals("/path/to/resource", result.get("Path"));
        assertEquals("query=123", result.get("Query"));
        assertEquals("fragment", result.get("Fragment"));
    }

    @Test
    public void testParseSimpleUrl() {
        Map<String, String> result = UrlParserUtil.parse("http://example.com");
        assertEquals("http", result.get("Scheme"));
        assertEquals("example.com", result.get("Host"));
        assertEquals("", result.get("Port"));
        assertEquals("", result.get("Path"));
        assertEquals("", result.get("Query"));
        assertEquals("", result.get("Fragment"));
    }

    @Test
    public void testInvalidUrl() {
        assertThrows(IllegalArgumentException.class, () -> UrlParserUtil.parse("invalid url //^#"));
    }
}
