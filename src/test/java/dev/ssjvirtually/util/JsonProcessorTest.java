package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonProcessorTest {

    @Test
    public void testYamlToJson() throws Exception {
        String yaml = "name: John Doe\nage: 30\ncity: New York";
        String json = JsonProcessor.yamlToJson(yaml);
        
        assertNotNull(json);
        assertTrue(json.contains("\"name\" : \"John Doe\""));
        assertTrue(json.contains("\"age\" : 30"));
        assertTrue(json.contains("\"city\" : \"New York\""));
    }

    @Test
    public void testFormatJson() throws Exception {
        String input = "{\"name\":\"John\",\"age\":30}";
        String formatted = JsonProcessor.format(input, 2, false);
        
        assertTrue(formatted.contains("\n  \"name\""));
    }

    @Test
    public void testJsonToYaml() throws Exception {
        String input = "{\"name\":\"Alice\",\"enabled\":true}";
        String yaml = JsonProcessor.jsonToYaml(input);

        assertNotNull(yaml);
        assertTrue(yaml.contains("name: \"Alice\""));
        assertTrue(yaml.contains("enabled: true"));
    }
}
