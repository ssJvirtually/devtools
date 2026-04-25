package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomUtilTest {

    @Test
    public void testGenerateRandomStringLength() {
        String result = RandomUtil.generateRandomString(10, true, true, true, true);
        assertEquals(10, result.length());
    }

    @Test
    public void testGenerateRandomStringCharset() {
        // Only digits
        String result = RandomUtil.generateRandomString(100, false, false, true, false);
        assertTrue(result.matches("[0-9]+"));
        
        // Only uppercase
        result = RandomUtil.generateRandomString(100, true, false, false, false);
        assertTrue(result.matches("[A-Z]+"));
    }

    @Test
    public void testEmptyCharset() {
        assertThrows(IllegalArgumentException.class, () -> 
            RandomUtil.generateRandomString(10, false, false, false, false));
    }

    @Test
    public void testZeroLength() {
        assertEquals("", RandomUtil.generateRandomString(0, true, true, true, true));
    }
}
