package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.TextCodecUtil;
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

public class UrlCodecView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public UrlCodecView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("URL Encode/Decode");
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
        inputArea = createEditor("Enter raw or encoded URL text...");
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
        return area;
    }

    private void encode() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        outputArea.replaceText(TextCodecUtil.urlEncode(input));
        clearError();
    }

    private void decode() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        try {
            outputArea.replaceText(TextCodecUtil.urlDecode(input));
            clearError();
        } catch (IllegalArgumentException ex) {
            showError("Invalid URL-encoded input");
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
