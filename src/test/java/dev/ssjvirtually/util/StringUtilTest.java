package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest {

    @Test
    public void testToCamelCase() {
        assertEquals("helloWorld", StringUtil.toCamelCase("hello world"));
        assertEquals("helloWorld", StringUtil.toCamelCase("Hello World"));
        assertEquals("helloWorld", StringUtil.toCamelCase("hello_world"));
        assertEquals("helloWorld", StringUtil.toCamelCase("hello-world"));
        assertEquals("helloWorld", StringUtil.toCamelCase("HELLO_WORLD"));
    }

    @Test
    public void testToPascalCase() {
        assertEquals("HelloWorld", StringUtil.toPascalCase("hello world"));
        assertEquals("HelloWorld", StringUtil.toPascalCase("hello_world"));
        assertEquals("HelloWorld", StringUtil.toPascalCase("hello-world"));
    }

    @Test
    public void testToSnakeCase() {
        assertEquals("hello_world", StringUtil.toSnakeCase("helloWorld"));
        assertEquals("hello_world", StringUtil.toSnakeCase("Hello World"));
        assertEquals("hello_world", StringUtil.toSnakeCase("hello-world"));
    }

    @Test
    public void testToKebabCase() {
        assertEquals("hello-world", StringUtil.toKebabCase("helloWorld"));
        assertEquals("hello-world", StringUtil.toKebabCase("hello_world"));
        assertEquals("hello-world", StringUtil.toKebabCase("Hello World"));
    }

    @Test
    public void testToConstantCase() {
        assertEquals("HELLO_WORLD", StringUtil.toConstantCase("helloWorld"));
        assertEquals("HELLO_WORLD", StringUtil.toConstantCase("hello_world"));
        assertEquals("HELLO_WORLD", StringUtil.toConstantCase("hello world"));
    }

    @Test
    public void testToTitleCase() {
        assertEquals("Hello World", StringUtil.toTitleCase("hello world"));
        assertEquals("Hello World", StringUtil.toTitleCase("helloWorld"));
        assertEquals("Hello World", StringUtil.toTitleCase("HELLO_WORLD"));
    }

    @Test
    public void testToSentenceCase() {
        assertEquals("Hello world", StringUtil.toSentenceCase("hello world"));
        assertEquals("Hello world", StringUtil.toSentenceCase("HELLO WORLD"));
    }

    @Test
    public void testInspect() {
        String input = "Hello World\nLine 2";
        String result = StringUtil.inspect(input);
        assertTrue(result.contains("Length: 18"));
        assertTrue(result.contains("Word Count: 4"));
        assertTrue(result.contains("Line Count: 2"));
        assertTrue(result.contains("Byte Size (UTF-8): 18 bytes"));
    }

    @Test
    public void testSortLines() {
        String input = "c\nb\na";
        assertEquals("a\nb\nc", StringUtil.sortLines(input, true));
        assertEquals("c\nb\na", StringUtil.sortLines(input, false));
    }

    @Test
    public void testReverseLines() {
        String input = "line1\nline2\nline3";
        assertEquals("line3\nline2\nline1", StringUtil.reverseLines(input));
    }

    @Test
    public void testDeduplicateLines() {
        String input = "a\nb\na\nc\nb";
        assertEquals("a\nb\nc", StringUtil.deduplicateLines(input));
    }
}
