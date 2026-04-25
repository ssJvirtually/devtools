package dev.ssjvirtually.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Iterator;
import java.util.Map;

public final class JsonToPhpUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonToPhpUtil() {}

    public static String convert(String json) throws Exception {
        if (json == null || json.trim().isEmpty()) {
            return "";
        }
        JsonNode rootNode = mapper.readTree(json);
        StringBuilder sb = new StringBuilder();
        sb.append("<?php\n\n$array = ");
        buildPhpArray(rootNode, sb, 1);
        sb.append(";\n");
        return sb.toString();
    }

    private static void buildPhpArray(JsonNode node, StringBuilder sb, int indent) {
        if (node.isObject()) {
            sb.append("[\n");
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                appendIndent(sb, indent);
                sb.append("'").append(escape(entry.getKey())).append("' => ");
                buildPhpArray(entry.getValue(), sb, indent + 1);
                if (fields.hasNext()) sb.append(",");
                sb.append("\n");
            }
            appendIndent(sb, indent - 1);
            sb.append("]");
        } else if (node.isArray()) {
            sb.append("[\n");
            for (int i = 0; i < node.size(); i++) {
                appendIndent(sb, indent);
                buildPhpArray(node.get(i), sb, indent + 1);
                if (i < node.size() - 1) sb.append(",");
                sb.append("\n");
            }
            appendIndent(sb, indent - 1);
            sb.append("]");
        } else if (node.isTextual()) {
            sb.append("'").append(escape(node.asText())).append("'");
        } else if (node.isNumber()) {
            sb.append(node.asText());
        } else if (node.isBoolean()) {
            sb.append(node.asBoolean() ? "true" : "false");
        } else if (node.isNull()) {
            sb.append("null");
        } else {
            sb.append("'").append(escape(node.asText())).append("'");
        }
    }

    private static void appendIndent(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append("    ");
        }
    }

    private static String escape(String text) {
        if (text == null) return "";
        return text.replace("\\", "\\\\").replace("'", "\\'");
    }
}
