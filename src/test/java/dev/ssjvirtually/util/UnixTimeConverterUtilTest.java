package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnixTimeConverterUtilTest {

    @Test
    public void testRoundTrip() {
        String dateTime = "2024-01-01 00:00:00";
        long unix = UnixTimeConverterUtil.localDateTimeToUnix(dateTime);
        String converted = UnixTimeConverterUtil.unixToLocalDateTimeString(unix);

        assertEquals(dateTime, converted);
    }
}
