package dev.ssjvirtually.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QrCodeUtilTest {

    @Test
    public void testGenerateAndReadQrCode() {
        String text = "Hello DevUtils";
        byte[] qrBytes = QrCodeUtil.generateQrCode(text, 200, 200);
        
        assertNotNull(qrBytes);
        assertTrue(qrBytes.length > 0);
        
        String decodedText = QrCodeUtil.readQrCode(qrBytes);
        assertEquals(text, decodedText);
    }

    @Test
    public void testEmptyInput() {
        byte[] qrBytes = QrCodeUtil.generateQrCode("", 200, 200);
        assertEquals(0, qrBytes.length);
        
        assertEquals("", QrCodeUtil.readQrCode(new byte[0]));
    }
}
