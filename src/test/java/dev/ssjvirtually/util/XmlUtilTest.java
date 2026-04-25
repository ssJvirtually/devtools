package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class XmlUtilTest {

    @Test
    public void testBeautify() {
        String xml = "<root><child>text</child></root>";
        String result = XmlUtil.beautify(xml);
        assertTrue(result.contains("<child>text</child>"));
        assertTrue(result.contains("    <child>"));
    }

    @Test
    public void testMinify() {
        String xml = "<root>\n    <child>text</child>\n</root>";
        String result = XmlUtil.minify(xml);
        assertEquals("<root><child>text</child></root>", result);
    }

    @Test
    public void testInvalidXml() {
        assertThrows(IllegalArgumentException.class, () -> XmlUtil.beautify("<root><child></root>"));
    }
}
