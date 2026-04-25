package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NumberBaseUtilTest {

    @Test
    public void testConvertHexToDecimal() {
        assertEquals("255", NumberBaseUtil.convert("FF", 16, 10));
        assertEquals("255", NumberBaseUtil.convert("0xFF", 16, 10));
        assertEquals("255", NumberBaseUtil.convert("ff", 16, 10));
    }

    @Test
    public void testConvertDecimalToBinary() {
        assertEquals("1010", NumberBaseUtil.convert("10", 10, 2));
    }

    @Test
    public void testConvertBinaryToOctal() {
        assertEquals("12", NumberBaseUtil.convert("1010", 2, 8));
        assertEquals("12", NumberBaseUtil.convert("0b1010", 2, 8));
    }

    @Test
    public void testInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> NumberBaseUtil.convert("XYZ", 10, 16));
    }
}
