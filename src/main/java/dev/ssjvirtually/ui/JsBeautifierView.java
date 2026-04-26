package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.JsBeautifierUtil;
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

public class JsBeautifierView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public JsBeautifierView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("JavaScript Beautify / Minify");
        title.getStyleClass().add("tool-title");

        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox actions = new HBox(10);
        Button beautifyButton = new Button("Beautify");
        Button minifyButton = new Button("Minify");
        beautifyButton.setOnAction(e -> beautify());
        minifyButton.setOnAction(e -> minify());
        actions.getChildren().addAll(beautifyButton, minifyButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Paste JavaScript here...");
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

    private void beautify() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        try {
            outputArea.replaceText(JsBeautifierUtil.beautify(input));
            clearError();
        } catch (Exception ex) {
            showError("Failed to beautify: " + ex.getMessage());
        }
    }

    private void minify() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        try {
            outputArea.replaceText(JsBeautifierUtil.minify(input));
            clearError();
        } catch (Exception ex) {
            showError("Failed to minify: " + ex.getMessage());
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
