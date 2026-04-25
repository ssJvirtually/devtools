package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.NumberBaseUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class NumberBaseConverterView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final ComboBox<Integer> fromBaseCombo;
    private final ComboBox<Integer> toBaseCombo;
    private final Label errorLabel;

    public NumberBaseConverterView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Number Base Converter");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox config = new HBox(10);
        config.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        fromBaseCombo = new ComboBox<>();
        fromBaseCombo.getItems().addAll(2, 8, 10, 16);
        fromBaseCombo.setValue(10);
        
        toBaseCombo = new ComboBox<>();
        toBaseCombo.getItems().addAll(2, 8, 10, 16);
        toBaseCombo.setValue(16);
        
        Button convertBtn = new Button("Convert");
        convertBtn.setOnAction(e -> convert());
        
        config.getChildren().addAll(
                new Label("From Base:"), fromBaseCombo,
                new Label("To Base:"), toBaseCombo,
                convertBtn
        );

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        
        inputArea = createEditor("Enter number...");
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

        getChildren().addAll(header, config, splitPane, errorLabel);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void convert() {
        try {
            String result = NumberBaseUtil.convert(
                    inputArea.getText(),
                    fromBaseCombo.getValue(),
                    toBaseCombo.getValue()
            );
            outputArea.replaceText(result);
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
