package dev.ssjvirtually.util;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonSyntaxHighlighter {

    private static final String KEY_PATTERN = "\"([^\"\\\\]|\\\\.)*\"\\s*(?=:)";
    private static final String STRING_PATTERN = "\"(?:[^\"\\\\]|\\\\.)*\"";
    private static final String NUMBER_PATTERN = "-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?";
    private static final String BOOLEAN_PATTERN = "true|false";
    private static final String NULL_PATTERN = "null";
    private static final String BRACE_PATTERN = "[\\{\\}\\[\\]]";
    private static final String COMMA_COLON_PATTERN = "[,:]";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEY>" + KEY_PATTERN + ")"
            + "|(?<STRING>" + STRING_PATTERN + ")"
            + "|(?<NUMBER>" + NUMBER_PATTERN + ")"
            + "|(?<BOOLEAN>" + BOOLEAN_PATTERN + ")"
            + "|(?<NULL>" + NULL_PATTERN + ")"
            + "|(?<BRACE>" + BRACE_PATTERN + ")"
            + "|(?<COMMACOLON>" + COMMA_COLON_PATTERN + ")"
    );

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEY") != null ? "json-key" :
                    matcher.group("STRING") != null ? "json-string" :
                    matcher.group("NUMBER") != null ? "json-number" :
                    matcher.group("BOOLEAN") != null ? "json-boolean" :
                    matcher.group("NULL") != null ? "json-null" :
                    matcher.group("BRACE") != null ? "json-brace" :
                    matcher.group("COMMACOLON") != null ? "json-comma-colon" :
                    null;
            
            assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}