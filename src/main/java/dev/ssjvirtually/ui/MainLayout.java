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
                default:
                    // For now, clear the center if it's not implemented
                    setCenter(null);
                    break;
            }
        });

        getStyleClass().add("main-layout");
    }
}
