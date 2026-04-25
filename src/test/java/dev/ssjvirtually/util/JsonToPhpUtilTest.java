package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JsonToPhpUtilTest {

    @Test
    public void testConvertObject() throws Exception {
        String json = "{\"name\":\"John\", \"age\":30}";
        String php = JsonToPhpUtil.convert(json);
        assertTrue(php.contains("<?php"));
        assertTrue(php.contains("'name' => 'John'"));
        assertTrue(php.contains("'age' => 30"));
    }

    @Test
    public void testConvertArray() throws Exception {
        String json = "[1, 2, 3]";
        String php = JsonToPhpUtil.convert(json);
        assertTrue(php.contains("1,"));
        assertTrue(php.contains("2,"));
        assertTrue(php.contains("3"));
        assertTrue(php.contains("["));
        assertTrue(php.contains("]"));
    }

    @Test
    public void testConvertNested() throws Exception {
        String json = "{\"users\": [{\"name\":\"A\"}]}";
        String php = JsonToPhpUtil.convert(json);
        assertTrue(php.contains("'users' => ["));
        assertTrue(php.contains("'name' => 'A'"));
    }
}
