package dev.ssjvirtually.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public final class XmlConverterUtil {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    private XmlConverterUtil() {
    }

    public static String xmlToJson(String xml) throws Exception {
        if (xml == null || xml.trim().isEmpty()) {
            return "";
        }
        JsonNode node = xmlMapper.readTree(xml.getBytes());
        return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }

    public static String jsonToXml(String json) throws Exception {
        if (json == null || json.trim().isEmpty()) {
            return "";
        }
        JsonNode node = jsonMapper.readTree(json);
        return xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
    }
}
