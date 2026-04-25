package dev.ssjvirtually.util;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class StringUtil {

    private StringUtil() {
    }

    public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = splitWords(input);
        if (words.length == 0) return "";
        StringBuilder sb = new StringBuilder(words[0].toLowerCase());
        for (int i = 1; i < words.length; i++) {
            sb.append(capitalize(words[i]));
        }
        return sb.toString();
    }

    public static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = splitWords(input);
        return Arrays.stream(words)
                .map(StringUtil::capitalize)
                .collect(Collectors.joining());
    }

    public static String toSnakeCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = splitWords(input);
        return Arrays.stream(words)
                .map(String::toLowerCase)
                .collect(Collectors.joining("_"));
    }

    public static String toKebabCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = splitWords(input);
        return Arrays.stream(words)
                .map(String::toLowerCase)
                .collect(Collectors.joining("-"));
    }

    public static String toConstantCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = splitWords(input);
        return Arrays.stream(words)
                .map(String::toUpperCase)
                .collect(Collectors.joining("_"));
    }

    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] words = splitWords(input);
        return Arrays.stream(words)
                .map(StringUtil::capitalize)
                .collect(Collectors.joining(" "));
    }

    public static String toSentenceCase(String input) {
        if (input == null || input.isEmpty()) return input;
        String lowered = input.toLowerCase();
        return capitalize(lowered);
    }

    private static String[] splitWords(String input) {
        // Split by non-alphanumeric characters or by camelCase/PascalCase boundaries
        return input.split("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])|[^a-zA-Z0-9]+");
    }

    private static String capitalize(String word) {
        if (word == null || word.isEmpty()) return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }

    public static String inspect(String input) {
        if (input == null) return "No input";
        int length = input.length();
        int wordCount = input.trim().isEmpty() ? 0 : input.trim().split("\\s+").length;
        int lineCount = input.isEmpty() ? 0 : input.split("\\r?\\n").length;
        int charCountNoSpaces = input.replace(" ", "").replace("\n", "").replace("\r", "").length();
        int byteCountUtf8 = input.getBytes(java.nio.charset.StandardCharsets.UTF_8).length;

        StringBuilder sb = new StringBuilder();
        sb.append("Length: ").append(length).append("\n");
        sb.append("Word Count: ").append(wordCount).append("\n");
        sb.append("Line Count: ").append(lineCount).append("\n");
        sb.append("Char Count (no spaces/newlines): ").append(charCountNoSpaces).append("\n");
        sb.append("Byte Size (UTF-8): ").append(byteCountUtf8).append(" bytes\n");
        
        return sb.toString();
    }

    public static String sortLines(String input, boolean ascending) {
        if (input == null || input.isEmpty()) return input;
        String[] lines = input.split("\\r?\\n");
        Arrays.sort(lines, (a, b) -> ascending ? a.compareTo(b) : b.compareTo(a));
        return String.join("\n", lines);
    }

    public static String reverseLines(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] lines = input.split("\\r?\\n");
        java.util.Collections.reverse(Arrays.asList(lines));
        return String.join("\n", lines);
    }

    public static String deduplicateLines(String input) {
        if (input == null || input.isEmpty()) return input;
        String[] lines = input.split("\\r?\\n");
        return Arrays.stream(lines)
                .distinct()
                .collect(Collectors.joining("\n"));
    }
}
