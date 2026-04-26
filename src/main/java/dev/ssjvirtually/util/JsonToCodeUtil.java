package dev.ssjvirtually.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class JsonToCodeUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private JsonToCodeUtil() {
    }

    public static String convertToPojo(String json, String rootClassName) {
        if (json == null || json.trim().isEmpty()) {
            return "";
        }

        try {
            JsonNode rootNode = mapper.readTree(json);
            StringBuilder sb = new StringBuilder();
            Set<String> generatedClasses = new HashSet<>();
            
            generateClass(rootNode, rootClassName, sb, generatedClasses);
            
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON: " + e.getMessage(), e);
        }
    }

    private static void generateClass(JsonNode node, String className, StringBuilder sb, Set<String> generatedClasses) {
        if (node.getNodeType() != JsonNodeType.OBJECT) {
            return;
        }

        if (generatedClasses.contains(className)) {
            return;
        }
        generatedClasses.add(className);

        sb.append("public class ").append(className).append(" {\n\n");

        Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            JsonNode fieldValue = field.getValue();
            String javaType = getJavaType(fieldName, fieldValue, sb, generatedClasses);

            sb.append("    private ").append(javaType).append(" ").append(fieldName).append(";\n");
        }

        sb.append("\n");
        
        // Add Getters and Setters (simplified)
        fields = node.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            String javaType = getJavaType(fieldName, field.getValue(), null, null); // Pass nulls to avoid re-generating
            String capitalized = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            sb.append("    public ").append(javaType).append(" get").append(capitalized).append("() {\n");
            sb.append("        return ").append(fieldName).append(";\n");
            sb.append("    }\n\n");

            sb.append("    public void set").append(capitalized).append("(").append(javaType).append(" ").append(fieldName).append(") {\n");
            sb.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
            sb.append("    }\n\n");
        }

        sb.append("}\n\n");
    }

    private static String getJavaType(String fieldName, JsonNode node, StringBuilder sb, Set<String> generatedClasses) {
        switch (node.getNodeType()) {
            case STRING:
                return "String";
            case NUMBER:
                if (node.isInt()) return "Integer";
                if (node.isLong()) return "Long";
                return "Double";
            case BOOLEAN:
                return "Boolean";
            case ARRAY:
                if (node.size() > 0) {
                    return "List<" + getJavaType(fieldName, node.get(0), sb, generatedClasses) + ">";
                }
                return "List<Object>";
            case OBJECT:
                String nestedClassName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                if (sb != null && generatedClasses != null) {
                    generateClass(node, nestedClassName, sb, generatedClasses);
                }
                return nestedClassName;
            default:
                return "Object";
        }
    }
}
