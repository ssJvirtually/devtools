package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.JsonProcessor;
import dev.ssjvirtually.util.JsonSyntaxHighlighter;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class JsonToYamlView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;
    private boolean isInternalUpdate = false;

    public JsonToYamlView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("JSON to YAML Converter");
        title.getStyleClass().add("tool-title");

        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);

        inputArea = createCodeEditor("Paste JSON here...");
        outputArea = createCodeEditor("YAML result will appear here...");
        outputArea.setEditable(false);

        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                new VirtualizedScrollPane<>(outputArea)
        );

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, splitPane, errorLabel);
        setupListeners();
    }

    private CodeArea createCodeEditor(String promptText) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(promptText));
        area.getStyleClass().add("code-editor");
        area.textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && !newText.isEmpty()) {
                try {
                    area.setStyleSpans(0, JsonSyntaxHighlighter.computeHighlighting(newText));
                } catch (Exception ignored) {
                    // Ignore highlighting failures while user is typing partial JSON.
                }
            }
        });
        return area;
    }

    private void setupListeners() {
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!isInternalUpdate) {
                convertJson();
            }
        });
    }

    private void convertJson() {
        String input = inputArea.getText();
        if (input == null || input.trim().isEmpty()) {
            isInternalUpdate = true;
            outputArea.clear();
            isInternalUpdate = false;
            clearError();
            return;
        }

        try {
            String result = JsonProcessor.jsonToYaml(input);
            isInternalUpdate = true;
            outputArea.replaceText(result);
            isInternalUpdate = false;
            clearError();
        } catch (Exception ex) {
            showError("Invalid JSON: " + ex.getMessage());
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    private void copyToClipboard() {
        ClipboardContent content = new ClipboardContent();
        content.putString(outputArea.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}
