package dev.ssjvirtually.ui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class MainLayout extends BorderPane {

    private final JsonToolView jsonToolView = new JsonToolView();
    private final YamlToJsonView yamlToJsonView = new YamlToJsonView();

    public MainLayout() {
        Sidebar sidebar = new Sidebar();

        setLeft(sidebar);
        setCenter(jsonToolView);
        
        sidebar.setOnToolSelected((obs, oldVal, newVal) -> {
            switch (newVal) {
                case "JSON Formatter":
                    setCenter(jsonToolView);
                    break;
                case "YAML to JSON":
                    setCenter(yamlToJsonView);
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