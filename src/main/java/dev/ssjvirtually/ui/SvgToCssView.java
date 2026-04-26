package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.SvgToCssUtil;
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

public class SvgToCssView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public SvgToCssView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("SVG to CSS Converter");
        title.getStyleClass().add("tool-title");

        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox actions = new HBox(10);
        Button dataUriButton = new Button("Data URI");
        Button backgroundButton = new Button("Background Image");
        Button maskButton = new Button("Mask Image");
        
        dataUriButton.setOnAction(e -> convert(1));
        backgroundButton.setOnAction(e -> convert(2));
        maskButton.setOnAction(e -> convert(3));
        
        actions.getChildren().addAll(dataUriButton, backgroundButton, maskButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Paste SVG code here...");
        outputArea = createEditor("CSS result appears here...");
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

    private void convert(int type) {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        try {
            String result = "";
            switch (type) {
                case 1: result = SvgToCssUtil.convertToDataUri(input); break;
                case 2: result = SvgToCssUtil.convertToBackgroundImage(input); break;
                case 3: result = SvgToCssUtil.convertToMaskImage(input); break;
            }
            outputArea.replaceText(result);
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
