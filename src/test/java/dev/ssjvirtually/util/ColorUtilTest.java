package dev.ssjvirtually.util;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ColorUtilTest {

    @Test
    public void testParseValidColors() {
        Color c1 = ColorUtil.parse("#FF0000");
        assertEquals(1.0, c1.getRed(), 0.01);
        
        Color c2 = ColorUtil.parse("red");
        assertEquals(1.0, c2.getRed(), 0.01);
        
        Color c3 = ColorUtil.parse("rgb(0, 255, 0)");
        assertEquals(1.0, c3.getGreen(), 0.01);
    }

    @Test
    public void testParseInvalidColor() {
        assertThrows(IllegalArgumentException.class, () -> ColorUtil.parse("not_a_color"));
    }

    @Test
    public void testFormats() {
        Color c = Color.web("#FF8000");
        assertEquals("#FF8000", ColorUtil.toHex(c));
        assertEquals("rgb(255, 128, 0)", ColorUtil.toRgb(c));
        assertEquals("rgba(255, 128, 0, 1.00)", ColorUtil.toRgba(c));
        assertEquals("hsb(30, 100%, 100%)", ColorUtil.toHsb(c));
    }
}
