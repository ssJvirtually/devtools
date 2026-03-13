package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.JsonProcessor;
import dev.ssjvirtually.util.JsonSyntaxHighlighter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.flowless.VirtualizedScrollPane;

public class YamlToJsonView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    private boolean isInternalUpdate = false;

    public YamlToJsonView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        // Header
        HBox header = new HBox(10);
        Label title = new Label("YAML to JSON Converter");
        title.getStyleClass().add("tool-title");
        
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(title, spacer, copyButton);

        // Editors Setup
        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);

        inputArea = createCodeEditor("Paste YAML here...");
        outputArea = createCodeEditor("JSON result will appear here...");
        outputArea.setEditable(false);

        VirtualizedScrollPane<CodeArea> inputScroll = new VirtualizedScrollPane<>(inputArea);
        VirtualizedScrollPane<CodeArea> outputScroll = new VirtualizedScrollPane<>(outputArea);

        splitPane.getItems().addAll(inputScroll, outputScroll);
        
        // Error Label
        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, splitPane, errorLabel);

        setupListeners();
    }

    private CodeArea createCodeEditor(String promptText) {
        CodeArea area = new CodeArea();
        // Removed LineNumberFactory to avoid NoSuchMethodError on internal APIs
        area.setPlaceholder(new Label(promptText));
        area.getStyleClass().add("code-editor");

        area.textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && !newText.isEmpty()) {
                try {
                    area.setStyleSpans(0, JsonSyntaxHighlighter.computeHighlighting(newText));
                } catch (Exception ignored) {
                    // Prevent crash on partial input during style computation
                }
            }
        });

        return area;
    }

    private void setupListeners() {
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isInternalUpdate) {
                convertYaml();
            }
        });
    }

    private void convertYaml() {
        String input = inputArea.getText();
        if (input == null || input.trim().isEmpty()) {
            isInternalUpdate = true;
            outputArea.clear();
            isInternalUpdate = false;
            clearError();
            return;
        }

        try {
            String result = JsonProcessor.yamlToJson(input);
            isInternalUpdate = true;
            outputArea.replaceText(result);
            isInternalUpdate = false;
            clearError();
            inputArea.getStyleClass().remove("error-border");
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        errorLabel.setText("Invalid YAML: " + message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        if (!inputArea.getStyleClass().contains("error-border")) {
            inputArea.getStyleClass().add("error-border");
        }
    }

    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        inputArea.getStyleClass().remove("error-border");
    }
    
    private void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(outputArea.getText());
        clipboard.setContent(content);
    }
}
