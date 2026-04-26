package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonToCodeUtilTest {

    @Test
    public void testSimpleConversion() {
        String json = "{\"name\":\"John\", \"age\":30, \"active\":true}";
        String code = JsonToCodeUtil.convertToPojo(json, "User");
        
        assertNotNull(code);
        assertTrue(code.contains("public class User {"));
        assertTrue(code.contains("private String name;"));
        assertTrue(code.contains("private Integer age;"));
        assertTrue(code.contains("private Boolean active;"));
        assertTrue(code.contains("public String getName()"));
    }

    @Test
    public void testNestedConversion() {
        String json = "{\"name\":\"John\", \"address\":{\"city\":\"NY\", \"zip\":10001}}";
        String code = JsonToCodeUtil.convertToPojo(json, "User");
        
        assertNotNull(code);
        assertTrue(code.contains("public class User {"));
        assertTrue(code.contains("private Address address;"));
        assertTrue(code.contains("public class Address {"));
        assertTrue(code.contains("private String city;"));
    }

    @Test
    public void testArrayConversion() {
        String json = "{\"tags\":[\"java\", \"json\"]}";
        String code = JsonToCodeUtil.convertToPojo(json, "Data");
        
        assertNotNull(code);
        assertTrue(code.contains("private List<String> tags;"));
    }

    @Test
    public void testEmptyInput() {
        assertEquals("", JsonToCodeUtil.convertToPojo("", "Root"));
        assertEquals("", JsonToCodeUtil.convertToPojo(null, "Root"));
    }

    @Test
    public void testInvalidJson() {
        assertThrows(IllegalArgumentException.class, () -> {
            JsonToCodeUtil.convertToPojo("{invalid", "Root");
        });
    }
}
