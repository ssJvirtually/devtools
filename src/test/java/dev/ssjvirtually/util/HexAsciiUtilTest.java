package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HexAsciiUtilTest {

    @Test
    public void testAsciiToHex() {
        assertEquals("68656c6c6f", HexAsciiUtil.asciiToHex("hello"));
    }

    @Test
    public void testHexToAscii() {
        assertEquals("hello", HexAsciiUtil.hexToAscii("68656c6c6f"));
    }
}
