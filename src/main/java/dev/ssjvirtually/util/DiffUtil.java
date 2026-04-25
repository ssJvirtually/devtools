package dev.ssjvirtually.util;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class DiffUtil {

    private DiffUtil() {
    }

    public static String computeDiff(String original, String revised) {
        if (original == null) original = "";
        if (revised == null) revised = "";
        
        List<String> originalLines = Arrays.asList(original.split("\\r?\\n"));
        List<String> revisedLines = Arrays.asList(revised.split("\\r?\\n"));

        Patch<String> patch = DiffUtils.diff(originalLines, revisedLines);
        List<AbstractDelta<String>> deltas = patch.getDeltas();

        StringBuilder sb = new StringBuilder();
        for (AbstractDelta<String> delta : deltas) {
            sb.append(delta.toString()).append("\n");
        }
        
        if (deltas.isEmpty()) {
            return "No differences found.";
        }
        
        return sb.toString();
    }
}
