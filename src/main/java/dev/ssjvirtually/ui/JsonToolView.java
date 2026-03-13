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

public class JsonToolView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;
    
    private final ComboBox<String> indentCombo;
    private final ToggleButton sortToggle;
    private final ToggleButton minifyToggle;

    private boolean isInternalUpdate = false;

    public JsonToolView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        // Header
        HBox header = new HBox(10);
        Label title = new Label("JSON Formatter");
        title.getStyleClass().add("tool-title");
        
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        
        // Spacer to push the copy button to the right
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        header.getChildren().addAll(title, spacer, copyButton);

        // Configuration Bar
        HBox configBar = new HBox(10);
        
        indentCombo = new ComboBox<>();
        indentCombo.getItems().addAll("2 Spaces", "4 Spaces");
        indentCombo.setValue("2 Spaces");
        
        sortToggle = new ToggleButton("Sort Keys");
        minifyToggle = new ToggleButton("Minify");
        
        configBar.getChildren().addAll(
            new Label("Indent:"), indentCombo,
            sortToggle, minifyToggle
        );

        // Editors Setup
        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);

        inputArea = createCodeEditor("Paste raw JSON here...");
        outputArea = createCodeEditor("Formatted JSON will appear here...");
        outputArea.setEditable(false);

        VirtualizedScrollPane<CodeArea> inputScroll = new VirtualizedScrollPane<>(inputArea);
        VirtualizedScrollPane<CodeArea> outputScroll = new VirtualizedScrollPane<>(outputArea);

        splitPane.getItems().addAll(inputScroll, outputScroll);
        
        // Error Label
        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, configBar, splitPane, errorLabel);

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
                processJson();
            }
        });
        indentCombo.valueProperty().addListener((obs, oldVal, newVal) -> processJson());
        sortToggle.selectedProperty().addListener((obs, oldVal, newVal) -> processJson());
        minifyToggle.selectedProperty().addListener((obs, oldVal, newVal) -> processJson());
    }

    private void processJson() {
        String input = inputArea.getText();
        if (input == null || input.trim().isEmpty()) {
            isInternalUpdate = true;
            outputArea.clear();
            isInternalUpdate = false;
            clearError();
            return;
        }

        try {
            int indentSpaces = indentCombo.getValue().equals("2 Spaces") ? 2 : 4;
            boolean sortKeys = sortToggle.isSelected();
            boolean minify = minifyToggle.isSelected();

            String result;
            if (minify) {
                result = JsonProcessor.minify(input);
            } else {
                result = JsonProcessor.format(input, indentSpaces, sortKeys);
            }

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
        errorLabel.setText("Invalid JSON: " + message);
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