package dev.ssjvirtually.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HtmlToJsxUtil {

    private HtmlToJsxUtil() {
    }

    public static String convertToJsx(String html) {
        if (html == null || html.trim().isEmpty()) {
            return "";
        }

        String jsx = html;

        // Replace class with className
        jsx = jsx.replaceAll("\\bclass=", "className=");

        // Replace for with htmlFor
        jsx = jsx.replaceAll("\\bfor=", "htmlFor=");

        // Replace style strings with placeholders (basic)
        // Note: Real conversion is much more complex, this is a helpful utility
        Pattern stylePattern = Pattern.compile("style=\"([^\"]+)\"");
        Matcher matcher = stylePattern.matcher(jsx);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String styleStr = matcher.group(1);
            String jsxStyle = convertStyleToJsx(styleStr);
            matcher.appendReplacement(sb, "style={{" + jsxStyle + "}}");
        }
        matcher.appendTail(sb);
        jsx = sb.toString();

        // Close self-closing tags that are often not closed in HTML
        String[] tags = {"img", "br", "hr", "input", "meta", "link"};
        for (String tag : tags) {
            jsx = jsx.replaceAll("<" + tag + "([^>]*?)(?<!/)>", "<" + tag + "$1 />");
        }

        return jsx;
    }

    private static String convertStyleToJsx(String styleStr) {
        String[] parts = styleStr.split(";");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            String[] kv = part.split(":");
            if (kv.length == 2) {
                String key = kv[0].trim();
                String value = kv[1].trim();
                
                // CamelCase the key
                String camelKey = toCamelCase(key);
                
                if (sb.length() > 0) sb.append(", ");
                sb.append(camelKey).append(": \"").append(value).append("\"");
            }
        }
        return sb.toString();
    }

    private static String toCamelCase(String str) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpper = false;
        for (char c : str.toCharArray()) {
            if (c == '-') {
                nextUpper = true;
            } else {
                if (nextUpper) {
                    sb.append(Character.toUpperCase(c));
                    nextUpper = false;
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
