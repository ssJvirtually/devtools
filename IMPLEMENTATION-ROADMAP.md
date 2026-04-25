# DevUtils OSS Implementation Roadmap

This roadmap is designed to be reusable with any LLM or contributor.  
Current baseline (as of 2026-04-25):
- Implemented before this roadmap: `JSON Formatter`, `YAML to JSON`
- Implemented in first execution pass: `Unix Time Converter`, `JSON to YAML`, `Base64 String Encode/Decode`, `JWT Debugger`, `URL Encode/Decode`

## 1) Architecture Pattern (Use This For Every Tool)

### UI pattern
- Add a new JavaFX view class in `src/main/java/dev/ssjvirtually/ui/*View.java`
- Use consistent layout:
  - Header (`tool-title`, Copy button if needed)
  - Optional config/action bar
  - Main content (split editors, form fields, preview panes)
  - Error label (`error-label`)
- Keep UI state local to the view.

### Logic pattern
- Put core transformation/parsing code in `src/main/java/dev/ssjvirtually/util/*Util.java`
- Keep tool logic deterministic and side-effect free where possible.
- Throw clear exceptions for invalid input.

### Wiring pattern
- Add tool name in `Sidebar`
- Add view instance + switch case in `MainLayout`
- Keep item names exactly matching the switch cases.

### Test pattern
- Add unit tests under `src/test/java/dev/ssjvirtually/util/`
- For each tool, test:
  - happy path
  - invalid input path
  - round-trip behavior (when relevant)

### Definition of done per tool
- Compiles and appears in sidebar
- UI handles empty and invalid input gracefully
- Core util has unit tests
- `mvn test` passes

## 2) Delivery Strategy

### Phase A: Core text/code transforms (fastest value)
1. Unix Time Converter
2. JSON Format/Validate
3. JSON to YAML
4. YAML to JSON
5. URL Encode/Decode
6. Base64 String Encode/Decode
7. Backslash Escape/Unescape
8. Hex to ASCII
9. ASCII to Hex
10. String Case Converter
11. String Inspector
12. Line Sort/Dedupe

### Phase B: Data format converters
1. JSON to CSV
2. CSV to JSON
3. PHP to JSON
4. JSON to PHP
5. PHP Serializer
6. PHP Unserializer
7. HTML Entity Encode/Decode
8. URL Parser
9. Number Base Converter
10. Color Converter

### Phase C: Crypto/identity/security
1. Hash Generator
2. UUID/ULID Generate/Decode
3. JWT Debugger
4. Certificate Decoder (X.509)
5. Random String Generator

### Phase D: Formatter/minifier suite
1. HTML Beautify/Minify
2. CSS Beautify/Minify
3. JS Beautify/Minify
4. ERB Beautify/Minify
5. LESS Beautify/Minify
6. SCSS Beautify/Minify
7. XML Beautify/Minify
8. SQL Formatter

### Phase E: Preview and generation tools
1. HTML Preview
2. Markdown Preview
3. QR Code Reader/Generator
4. Lorem Ipsum Generator
5. Text Diff Checker
6. SVG to CSS
7. HTML to JSX

### Phase F: Advanced parser/transpiler tools
1. RegExp Tester
2. Cron Job Parser
3. cURL to Code
4. JSON to Code

## 3) Feature-by-Feature Implementation Checklist

For each new tool:
1. Create `*Util` class with pure methods and clear errors.
2. Create `*View` class:
  - input controls
  - action button(s) or reactive listener
  - output display
  - copy button
  - error message handling
3. Register in `Sidebar` and `MainLayout`.
4. Add `*UtilTest`.
5. Run `mvn test`.
6. Manual smoke test in JavaFX app.

## 4) Suggested Dependency Plan (add only when needed)

- Already present: Jackson core/databind + YAML, RichTextFX
- Add later as needed:
  - Commons CSV: CSV converters
  - BouncyCastle: X.509 decode
  - ZXing: QR read/generate
  - JavaParser or Nashorn-compatible strategy: cURL/JSON to code generators
  - diffutils/java-diff-utils: text diff
  - ANTLR/cron-utils: cron parser
  - jsoup: HTML utilities
  - commonmark/flexmark: markdown preview

Keep native-image compatibility in mind for each dependency and document reflection config changes in `src/main/resources/META-INF/native-image/`.

## 5) Testing Matrix

- Unit tests: every util class
- Integration smoke:
  - launch app
  - open each tool
  - test one valid + one invalid sample
- Native checks:
  - build shaded JAR
  - build GraalVM native image
  - verify critical tools still run

## 6) Current Progress Tracker

- Completed:
  - Unix Time Converter
  - JSON Formatter
  - JSON to YAML
  - YAML to JSON
  - Base64 String Encode/Decode
  - JWT Debugger
  - URL Encode/Decode
  - Backslash Escape/Unescape
  - Hex to ASCII / ASCII to Hex
  - Hash Generator
  - UUID/ULID Generate/Decode
  - String Case Converter
  - String Inspector
  - Line Sort/Dedupe
- Next recommended batch:
  - JSON to CSV
  - CSV to JSON
  - HTML Entity Encode/Decode
  - Random String Generator
