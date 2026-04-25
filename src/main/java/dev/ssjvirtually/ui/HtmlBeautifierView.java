package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.HtmlBeautifierUtil;
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

public class HtmlBeautifierView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public HtmlBeautifierView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("HTML Beautify / Minify");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        inputArea = createEditor("Enter HTML...");
        outputArea = createEditor("Result appears here...");
        outputArea.setEditable(false);

        HBox config = new HBox(10);
        Button beautifyBtn = new Button("Beautify");
        beautifyBtn.setOnAction(e -> beautify());
        Button minifyBtn = new Button("Minify");
        minifyBtn.setOnAction(e -> minify());
        config.getChildren().addAll(beautifyBtn, minifyBtn);

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

        getChildren().addAll(header, config, splitPane, errorLabel);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void beautify() {
        try {
            String result = HtmlBeautifierUtil.beautify(inputArea.getText());
            outputArea.replaceText(result);
            clearError();
        } catch (Exception ex) {
            showError("Beautify error: " + ex.getMessage());
        }
    }

    private void minify() {
        try {
            String result = HtmlBeautifierUtil.minify(inputArea.getText());
            outputArea.replaceText(result);
            clearError();
        } catch (Exception ex) {
            showError("Minify error: " + ex.getMessage());
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
