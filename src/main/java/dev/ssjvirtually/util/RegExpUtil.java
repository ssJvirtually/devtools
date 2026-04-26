package dev.ssjvirtually.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegExpUtil {

    private RegExpUtil() {
    }

    public static class MatchResult {
        private final int start;
        private final int end;
        private final String content;
        private final List<String> groups;

        public MatchResult(int start, int end, String content, List<String> groups) {
            this.start = start;
            this.end = end;
            this.content = content;
            this.groups = groups;
        }

        public int getStart() { return start; }
        public int getEnd() { return end; }
        public String getContent() { return content; }
        public List<String> getGroups() { return groups; }
    }

    public static List<MatchResult> findMatches(String regex, String text, int flags) {
        if (regex == null || regex.isEmpty() || text == null) {
            return new ArrayList<>();
        }

        try {
            Pattern pattern = Pattern.compile(regex, flags);
            Matcher matcher = pattern.matcher(text);
            List<MatchResult> results = new ArrayList<>();

            while (matcher.find()) {
                List<String> groups = new ArrayList<>();
                for (int i = 0; i <= matcher.groupCount(); i++) {
                    groups.add(matcher.group(i));
                }
                results.add(new MatchResult(matcher.start(), matcher.end(), matcher.group(), groups));
            }
            return results;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex: " + e.getMessage(), e);
        }
    }
}
