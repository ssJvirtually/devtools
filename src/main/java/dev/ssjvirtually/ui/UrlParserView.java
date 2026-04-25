package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.UrlParserUtil;
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

import java.util.Map;

public class UrlParserView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public UrlParserView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("URL Parser");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        inputArea = createEditor("Enter URL to parse...");
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> parse());
        
        outputArea = createEditor("Parsed components appear here...");
        outputArea.setEditable(false);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                new VirtualizedScrollPane<>(outputArea)
        );

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, splitPane, errorLabel);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void parse() {
        String input = inputArea.getText();
        if (input == null || input.trim().isEmpty()) {
            outputArea.replaceText("");
            clearError();
            return;
        }
        
        try {
            Map<String, String> components = UrlParserUtil.parse(input);
            StringBuilder sb = new StringBuilder();
            components.forEach((key, value) -> {
                sb.append(String.format("%-10s: %s\n", key, value));
            });
            
            outputArea.replaceText(sb.toString());
            clearError();
        } catch (Exception ex) {
            showError(ex.getMessage());
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
