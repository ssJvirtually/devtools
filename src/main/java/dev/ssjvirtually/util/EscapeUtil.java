package dev.ssjvirtually.util;

public final class EscapeUtil {

    private EscapeUtil() {
    }

    public static String escapeBackslashes(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
    }

    public static String unescapeBackslashes(String input) {
        if (input == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(input.length());
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\\' && i + 1 < input.length()) {
                char next = input.charAt(i + 1);
                switch (next) {
                    case 'n':
                        out.append('\n');
                        i++;
                        break;
                    case 'r':
                        out.append('\r');
                        i++;
                        break;
                    case 't':
                        out.append('\t');
                        i++;
                        break;
                    case '"':
                        out.append('"');
                        i++;
                        break;
                    case '\\':
                        out.append('\\');
                        i++;
                        break;
                    default:
                        out.append(c);
                }
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }
}
