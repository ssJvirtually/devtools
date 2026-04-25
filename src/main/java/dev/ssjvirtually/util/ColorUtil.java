package dev.ssjvirtually.util;

import javafx.scene.paint.Color;
import java.util.Locale;

public final class ColorUtil {

    private ColorUtil() {
    }

    public static Color parse(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Color input cannot be empty");
        }
        try {
            return Color.web(input.trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid color format: " + input);
        }
    }

    public static String toHex(Color color) {
        if (color == null) return "";
        return String.format(Locale.ROOT, "#%02X%02X%02X",
                (int) Math.round(color.getRed() * 255),
                (int) Math.round(color.getGreen() * 255),
                (int) Math.round(color.getBlue() * 255));
    }

    public static String toRgb(Color color) {
        if (color == null) return "";
        return String.format(Locale.ROOT, "rgb(%d, %d, %d)",
                (int) Math.round(color.getRed() * 255),
                (int) Math.round(color.getGreen() * 255),
                (int) Math.round(color.getBlue() * 255));
    }

    public static String toRgba(Color color) {
        if (color == null) return "";
        return String.format(Locale.ROOT, "rgba(%d, %d, %d, %.2f)",
                (int) Math.round(color.getRed() * 255),
                (int) Math.round(color.getGreen() * 255),
                (int) Math.round(color.getBlue() * 255),
                color.getOpacity());
    }

    public static String toHsb(Color color) {
        if (color == null) return "";
        return String.format(Locale.ROOT, "hsb(%.0f, %d%%, %d%%)",
                color.getHue(),
                (int) Math.round(color.getSaturation() * 100),
                (int) Math.round(color.getBrightness() * 100));
    }
}
