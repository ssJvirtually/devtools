package dev.ssjvirtually.util;

import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.SourceFile;
import com.google.javascript.jscomp.WarningLevel;

import java.util.Collections;

public final class JsBeautifierUtil {

    private JsBeautifierUtil() {
    }

    public static String beautify(String js) {
        if (js == null || js.trim().isEmpty()) {
            return "";
        }

        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();
        
        // Pretty print settings
        options.setEmitUseStrict(false);
        options.setPrettyPrint(true);
        
        WarningLevel.QUIET.setOptionsForWarningLevel(options);

        SourceFile input = SourceFile.fromCode("input.js", js);
        compiler.compile(Collections.emptyList(), Collections.singletonList(input), options);

        if (compiler.hasErrors()) {
            throw new IllegalArgumentException("Invalid JavaScript: " + compiler.getErrors().get(0).toString());
        }

        return compiler.toSource();
    }

    public static String minify(String js) {
        if (js == null || js.trim().isEmpty()) {
            return "";
        }

        Compiler compiler = new Compiler();
        CompilerOptions options = new CompilerOptions();
        
        CompilationLevel.SIMPLE_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
        WarningLevel.QUIET.setOptionsForWarningLevel(options);

        SourceFile input = SourceFile.fromCode("input.js", js);
        compiler.compile(Collections.emptyList(), Collections.singletonList(input), options);

        if (compiler.hasErrors()) {
            throw new IllegalArgumentException("Invalid JavaScript: " + compiler.getErrors().get(0).toString());
        }

        return compiler.toSource();
    }
}
