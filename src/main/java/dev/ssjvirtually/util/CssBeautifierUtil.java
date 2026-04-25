package dev.ssjvirtually.util;

import com.helger.css.ECSSVersion;
import com.helger.css.decl.CascadingStyleSheet;
import com.helger.css.reader.CSSReader;
import com.helger.css.writer.CSSWriter;
import com.helger.css.writer.CSSWriterSettings;

import java.nio.charset.StandardCharsets;

public final class CssBeautifierUtil {

    private CssBeautifierUtil() {
    }

    public static String beautify(String css) {
        if (css == null || css.trim().isEmpty()) {
            return "";
        }
        CascadingStyleSheet aCSS = CSSReader.readFromString(css, StandardCharsets.UTF_8, ECSSVersion.CSS30);
        if (aCSS == null) {
            throw new IllegalArgumentException("Invalid CSS");
        }
        CSSWriterSettings aSettings = new CSSWriterSettings(ECSSVersion.CSS30, false);
        aSettings.setIndent("    ");
        CSSWriter aWriter = new CSSWriter(aSettings);
        return aWriter.getCSSAsString(aCSS);
    }

    public static String minify(String css) {
        if (css == null || css.trim().isEmpty()) {
            return "";
        }
        CascadingStyleSheet aCSS = CSSReader.readFromString(css, StandardCharsets.UTF_8, ECSSVersion.CSS30);
        if (aCSS == null) {
            throw new IllegalArgumentException("Invalid CSS");
        }
        CSSWriterSettings aSettings = new CSSWriterSettings(ECSSVersion.CSS30, true);
        CSSWriter aWriter = new CSSWriter(aSettings);
        return aWriter.getCSSAsString(aCSS);
    }
}
