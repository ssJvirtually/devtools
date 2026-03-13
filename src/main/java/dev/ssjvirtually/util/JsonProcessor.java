package dev.ssjvirtually.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;

public class JsonProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final YAMLMapper YAML_MAPPER = new YAMLMapper();

    public static String format(String input, int indentSpaces, boolean sortKeys) throws JsonProcessingException {
        ObjectMapper localMapper = new ObjectMapper();
        
        if (sortKeys) {
            localMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        }

        JsonNode tree = localMapper.readTree(input);

        DefaultPrettyPrinter.Indenter indenter = new DefaultPrettyPrinter.NopIndenter() {
            @Override
            public void writeIndentation(com.fasterxml.jackson.core.JsonGenerator jg, int level) throws java.io.IOException {
                jg.writeRaw("\n");
                for (int i = 0; i < level * indentSpaces; i++) {
                    jg.writeRaw(" ");
                }
            }
            @Override
            public boolean isInline() {
                return false;
            }
        };

        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);

        return localMapper.writer(printer).writeValueAsString(tree);
    }

    public static String minify(String input) throws JsonProcessingException {
        JsonNode tree = MAPPER.readTree(input);
        return MAPPER.writeValueAsString(tree);
    }
    
    public static void validate(String input) throws JsonProcessingException {
        MAPPER.readTree(input);
    }

    public static String yamlToJson(String yaml) throws JsonProcessingException {
        Object obj = YAML_MAPPER.readValue(yaml, Object.class);
        return MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}