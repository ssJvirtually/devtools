package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

public class RegExpUtilTest {

    @Test
    public void testFindMatches() {
        String regex = "(\\d+)";
        String text = "I have 2 apples and 10 oranges";
        List<RegExpUtil.MatchResult> matches = RegExpUtil.findMatches(regex, text, 0);

        assertEquals(2, matches.size());
        assertEquals("2", matches.get(0).getContent());
        assertEquals("10", matches.get(1).getContent());
        assertEquals(1, matches.get(0).getGroups().size() - 1); // 1 capturing group
        assertEquals("2", matches.get(0).getGroups().get(1));
    }

    @Test
    public void testCaseInsensitive() {
        String regex = "apple";
        String text = "APPLE apple Apple";
        List<RegExpUtil.MatchResult> matches = RegExpUtil.findMatches(regex, text, Pattern.CASE_INSENSITIVE);
        assertEquals(3, matches.size());
    }

    @Test
    public void testEmptyInput() {
        assertTrue(RegExpUtil.findMatches("", "text", 0).isEmpty());
        assertTrue(RegExpUtil.findMatches("regex", null, 0).isEmpty());
    }

    @Test
    public void testInvalidRegex() {
        assertThrows(IllegalArgumentException.class, () -> {
            RegExpUtil.findMatches("[", "text", 0);
        });
    }
}
