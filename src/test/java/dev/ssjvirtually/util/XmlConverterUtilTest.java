package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class XmlConverterUtilTest {

    @Test
    public void testXmlToJson() throws Exception {
        String xml = "<root><name>John</name><age>30</age></root>";
        String json = XmlConverterUtil.xmlToJson(xml);
        assertTrue(json.contains("\"name\" : \"John\""));
        assertTrue(json.contains("\"age\" : \"30\""));
    }

    @Test
    public void testJsonToXml() throws Exception {
        String json = "{\"name\":\"John\",\"age\":30}";
        String xml = XmlConverterUtil.jsonToXml(json);
        assertTrue(xml.contains("<name>John</name>"));
        assertTrue(xml.contains("<age>30</age>"));
    }
}
