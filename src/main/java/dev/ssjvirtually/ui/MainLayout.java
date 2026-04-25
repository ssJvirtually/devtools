package dev.ssjvirtually.ui;

import javafx.scene.layout.BorderPane;

public class MainLayout extends BorderPane {

    private final JsonToolView jsonToolView = new JsonToolView();
    private final JsonToYamlView jsonToYamlView = new JsonToYamlView();
    private final YamlToJsonView yamlToJsonView = new YamlToJsonView();
    private final Base64ToolView base64ToolView = new Base64ToolView();
    private final UrlCodecView urlCodecView = new UrlCodecView();
    private final JwtDebuggerView jwtDebuggerView = new JwtDebuggerView();
    private final UnixTimeConverterView unixTimeConverterView = new UnixTimeConverterView();
    private final HashGeneratorView hashGeneratorView = new HashGeneratorView();
    private final BackslashEscapeView backslashEscapeView = new BackslashEscapeView();
    private final HexAsciiView hexAsciiView = new HexAsciiView();
    private final UuidUlidView uuidUlidView = new UuidUlidView();
    private final StringCaseConverterView stringCaseConverterView = new StringCaseConverterView();
    private final StringInspectorView stringInspectorView = new StringInspectorView();
    private final LineSortDedupeView lineSortDedupeView = new LineSortDedupeView();
    private final JsonCsvView jsonCsvView = new JsonCsvView();
    private final HtmlEntityView htmlEntityView = new HtmlEntityView();
    private final RandomStringView randomStringView = new RandomStringView();
    private final JsonToPhpView jsonToPhpView = new JsonToPhpView();
    private final NumberBaseConverterView numberBaseConverterView = new NumberBaseConverterView();
    private final ColorConverterView colorConverterView = new ColorConverterView();
    private final UrlParserView urlParserView = new UrlParserView();

    public MainLayout() {
        Sidebar sidebar = new Sidebar();

        setLeft(sidebar);
        setCenter(jsonToolView);
        
        sidebar.setOnToolSelected((obs, oldVal, newVal) -> {
            switch (newVal) {
                case "JSON Formatter":
                    setCenter(jsonToolView);
                    break;
                case "JSON to YAML":
                    setCenter(jsonToYamlView);
                    break;
                case "YAML to JSON":
                    setCenter(yamlToJsonView);
                    break;
                case "Base64 String Encode/Decode":
                    setCenter(base64ToolView);
                    break;
                case "URL Encode/Decode":
                    setCenter(urlCodecView);
                    break;
                case "JWT Debugger":
                    setCenter(jwtDebuggerView);
                    break;
                case "Unix Time Converter":
                    setCenter(unixTimeConverterView);
                    break;
                case "Hash Generator":
                    setCenter(hashGeneratorView);
                    break;
                case "Backslash Escape/Unescape":
                    setCenter(backslashEscapeView);
                    break;
                case "Hex to ASCII / ASCII to Hex":
                    setCenter(hexAsciiView);
                    break;
                case "UUID/ULID Generate/Decode":
                    setCenter(uuidUlidView);
                    break;
                case "String Case Converter":
                    setCenter(stringCaseConverterView);
                    break;
                case "String Inspector":
                    setCenter(stringInspectorView);
                    break;
                case "Line Sort / Dedupe":
                    setCenter(lineSortDedupeView);
                    break;
                case "JSON / CSV Converter":
                    setCenter(jsonCsvView);
                    break;
                case "HTML Entity Encode/Decode":
                    setCenter(htmlEntityView);
                    break;
                case "Random String Generator":
                    setCenter(randomStringView);
                    break;
                case "JSON to PHP Array":
                    setCenter(jsonToPhpView);
                    break;
                case "Number Base Converter":
                    setCenter(numberBaseConverterView);
                    break;
                case "Color Converter":
                    setCenter(colorConverterView);
                    break;
                case "URL Parser":
                    setCenter(urlParserView);
                    break;
                default:
                    // For now, clear the center if it's not implemented
                    setCenter(null);
                    break;
            }
        });

        getStyleClass().add("main-layout");
    }
}
