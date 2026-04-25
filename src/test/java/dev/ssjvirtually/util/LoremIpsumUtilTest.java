package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoremIpsumUtilTest {

    @Test
    public void testGenerateWords() {
        String result = LoremIpsumUtil.generate(5, "words");
        String[] words = result.split("\\s+");
        assertEquals(5, words.length);
    }

    @Test
    public void testGenerateSentences() {
        String result = LoremIpsumUtil.generate(2, "sentences");
        assertTrue(result.contains("."));
    }

    @Test
    public void testGenerateParagraphs() {
        String result = LoremIpsumUtil.generate(2, "paragraphs");
        assertTrue(result.contains("\n\n"));
    }

    @Test
    public void testInvalidType() {
        assertThrows(IllegalArgumentException.class, () -> LoremIpsumUtil.generate(1, "invalid"));
    }
}
