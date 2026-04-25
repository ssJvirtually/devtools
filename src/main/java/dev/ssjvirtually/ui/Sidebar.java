package dev.ssjvirtually.ui;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class Sidebar extends VBox {

    private final ListView<String> toolList;

    public Sidebar() {
        setPrefWidth(260);
        getStyleClass().add("sidebar");

        toolList = new ListView<>();
        toolList.getItems().addAll(
            "Unix Time Converter",
            "JSON Formatter",
            "JSON to YAML",
            "YAML to JSON",
            "Base64 String Encode/Decode",
            "Hash Generator",
            "JWT Debugger",
            "URL Encode/Decode",
            "Backslash Escape/Unescape",
            "Hex to ASCII / ASCII to Hex",
            "UUID/ULID Generate/Decode",
            "String Case Converter",
            "String Inspector",
            "Line Sort / Dedupe"
        );
        toolList.getSelectionModel().select("JSON Formatter");
        
        getChildren().add(toolList);
        
        // Expand the list to fill the sidebar
        javafx.scene.layout.VBox.setVgrow(toolList, javafx.scene.layout.Priority.ALWAYS);
    }

    public void setOnToolSelected(ChangeListener<String> listener) {
        toolList.getSelectionModel().selectedItemProperty().addListener(listener);
    }
}
