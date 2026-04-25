package dev.ssjvirtually.util;

import java.util.Random;

public final class LoremIpsumUtil {

    private static final String[] WORDS = {
            "lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed", "do",
            "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua", "ut",
            "enim", "ad", "minim", "veniam", "quis", "nostrud", "exercitation", "ullamco", "laboris",
            "nisi", "ut", "aliquip", "ex", "ea", "commodo", "consequat", "duis", "aute", "irure", "dolor",
            "in", "reprehenderit", "in", "voluptate", "velit", "esse", "cillum", "dolore", "eu", "fugiat",
            "nulla", "pariatur", "excepteur", "sint", "occaecat", "cupidatat", "non", "proident", "sunt",
            "in", "culpa", "qui", "officia", "deserunt", "mollit", "anim", "id", "est", "laborum"
    };

    private static final Random RANDOM = new Random();

    private LoremIpsumUtil() {
    }

    public static String generate(int count, String type) {
        if (count <= 0) return "";
        
        StringBuilder sb = new StringBuilder();
        switch (type.toLowerCase()) {
            case "words":
                for (int i = 0; i < count; i++) {
                    sb.append(WORDS[RANDOM.nextInt(WORDS.length)]).append(" ");
                }
                break;
            case "sentences":
                for (int i = 0; i < count; i++) {
                    sb.append(generateSentence()).append(" ");
                }
                break;
            case "paragraphs":
                for (int i = 0; i < count; i++) {
                    sb.append(generateParagraph()).append("\n\n");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
        return sb.toString().trim();
    }

    private static String generateSentence() {
        int length = RANDOM.nextInt(10) + 5;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            String word = WORDS[RANDOM.nextInt(WORDS.length)];
            if (i == 0) {
                word = word.substring(0, 1).toUpperCase() + word.substring(1);
            }
            sb.append(word).append(i == length - 1 ? "." : " ");
        }
        return sb.toString();
    }

    private static String generateParagraph() {
        int length = RANDOM.nextInt(5) + 3;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(generateSentence()).append(" ");
        }
        return sb.toString().trim();
    }
}
