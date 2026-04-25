package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EscapeUtilTest {

    @Test
    public void testEscapeUnescapeRoundTrip() {
        String input = "hello\t\"world\"\nline2\\done";
        String escaped = EscapeUtil.escapeBackslashes(input);
        String unescaped = EscapeUtil.unescapeBackslashes(escaped);
        assertEquals(input, unescaped);
    }
}
