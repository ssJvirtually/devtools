package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.TextCodecUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class Base64ToolView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public Base64ToolView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Base64 String Encode/Decode");
        title.getStyleClass().add("tool-title");

        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox actions = new HBox(10);
        Button encodeButton = new Button("Encode");
        Button decodeButton = new Button("Decode");
        encodeButton.setOnAction(e -> encode());
        decodeButton.setOnAction(e -> decode());
        actions.getChildren().addAll(encodeButton, decodeButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Enter plain text or Base64...");
        outputArea = createEditor("Result appears here...");
        outputArea.setEditable(false);
        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                new VirtualizedScrollPane<>(outputArea)
        );

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, actions, splitPane, errorLabel);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        area.textProperty().addListener((obs, oldText, newText) -> {
            if (newText != null && !newText.isEmpty()) {
                try {
                    // Only attempt highlighting if it looks like JSON
                    if (newText.trim().startsWith("{") || newText.trim().startsWith("[")) {
                        area.setStyleSpans(0, dev.ssjvirtually.util.JsonSyntaxHighlighter.computeHighlighting(newText));
                    } else {
                        // Clear styling
                        area.setStyleSpans(0, org.fxmisc.richtext.model.StyleSpans.singleton(java.util.Collections.emptyList(), newText.length()));
                    }
                } catch (Exception ignored) {
                }
            }
        });
        return area;
    }

    private void encode() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        outputArea.replaceText(TextCodecUtil.base64Encode(input));
        clearError();
    }

    private void decode() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        try {
            outputArea.replaceText(TextCodecUtil.base64Decode(input.trim()));
            clearError();
        } catch (IllegalArgumentException ex) {
            showError("Invalid Base64 input");
        }
    }

    private void copyToClipboard() {
        ClipboardContent content = new ClipboardContent();
        content.putString(outputArea.getText());
        Clipboard.getSystemClipboard().setContent(content);
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
}
