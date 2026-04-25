package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.CsvUtil;
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

public class JsonCsvView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public JsonCsvView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("JSON / CSV Converter");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        inputArea = createEditor("Enter JSON or CSV...");
        outputArea = createEditor("Result appears here...");
        outputArea.setEditable(false);

        HBox config = new HBox(10);
        Button jsonToCsvBtn = new Button("JSON to CSV");
        jsonToCsvBtn.setOnAction(e -> convertJsonToCsv());
        Button csvToJsonBtn = new Button("CSV to JSON");
        csvToJsonBtn.setOnAction(e -> convertCsvToJson());
        config.getChildren().addAll(jsonToCsvBtn, csvToJsonBtn);

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

    private void convertJsonToCsv() {
        try {
            String result = CsvUtil.jsonToCsv(inputArea.getText());
            outputArea.replaceText(result);
            clearError();
        } catch (Exception ex) {
            showError("JSON to CSV error: " + ex.getMessage());
        }
    }

    private void convertCsvToJson() {
        try {
            String result = CsvUtil.csvToJson(inputArea.getText());
            outputArea.replaceText(result);
            clearError();
        } catch (Exception ex) {
            showError("CSV to JSON error: " + ex.getMessage());
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
