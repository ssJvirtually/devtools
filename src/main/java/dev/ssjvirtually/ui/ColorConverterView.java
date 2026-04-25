package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.ColorUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class ColorConverterView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public ColorConverterView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Color Converter");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        inputArea = createEditor("Enter color (e.g., #FF0000, rgb(255,0,0), red)...");
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> convert());
        
        outputArea = createEditor("Formats appear here...");
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

    private void convert() {
        String input = inputArea.getText();
        if (input == null || input.trim().isEmpty()) {
            outputArea.replaceText("");
            clearError();
            return;
        }
        
        try {
            Color color = ColorUtil.parse(input);
            StringBuilder sb = new StringBuilder();
            sb.append("HEX:  ").append(ColorUtil.toHex(color)).append("\n");
            sb.append("RGB:  ").append(ColorUtil.toRgb(color)).append("\n");
            sb.append("RGBA: ").append(ColorUtil.toRgba(color)).append("\n");
            sb.append("HSB:  ").append(ColorUtil.toHsb(color)).append("\n");
            
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
