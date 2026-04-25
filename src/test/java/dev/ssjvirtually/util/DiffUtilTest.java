package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DiffUtilTest {

    @Test
    public void testComputeDiff() {
        String original = "line1\nline2";
        String revised = "line1\nline3";
        String result = DiffUtil.computeDiff(original, revised);
        assertTrue(result.contains("line2"));
        assertTrue(result.contains("line3"));
    }

    @Test
    public void testNoDiff() {
        String text = "line1\nline2";
        String result = DiffUtil.computeDiff(text, text);
        assertEquals("No differences found.", result);
    }
}
