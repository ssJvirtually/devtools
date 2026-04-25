package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextCodecUtilTest {

    @Test
    public void testBase64RoundTrip() {
        String input = "hello world";
        String encoded = TextCodecUtil.base64Encode(input);
        String decoded = TextCodecUtil.base64Decode(encoded);

        assertEquals(input, decoded);
    }

    @Test
    public void testUrlRoundTrip() {
        String input = "a+b test@devutils.com";
        String encoded = TextCodecUtil.urlEncode(input);
        String decoded = TextCodecUtil.urlDecode(encoded);

        assertEquals(input, decoded);
    }
}
