package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.JsonToCodeUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class JsonToCodeView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final TextField classNameField;
    private final Label errorLabel;

    public JsonToCodeView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("JSON to Code (POJO)");
        title.getStyleClass().add("tool-title");

        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox actions = new HBox(10);
        classNameField = new TextField("Root");
        classNameField.setPromptText("Root Class Name");
        Button convertButton = new Button("Generate Java POJO");
        convertButton.setOnAction(e -> convert());
        actions.getChildren().addAll(new Label("Class Name:"), classNameField, convertButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Paste JSON here...");
        outputArea = createEditor("Generated code appears here...");
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

    private void convert() {
        String input = inputArea.getText();
        String className = classNameField.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        if (className == null || className.trim().isEmpty()) {
            className = "Root";
        }
        try {
            outputArea.replaceText(JsonToCodeUtil.convertToPojo(input, className));
            clearError();
        } catch (Exception ex) {
            showError("Failed to convert: " + ex.getMessage());
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
