package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.RandomUtil;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class RandomStringView extends VBox {

    private final CodeArea outputArea;
    private final Spinner<Integer> lengthSpinner;
    private final CheckBox upperCheck;
    private final CheckBox lowerCheck;
    private final CheckBox digitsCheck;
    private final CheckBox symbolsCheck;
    private final Label errorLabel;

    public RandomStringView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Random String Generator");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        VBox config = new VBox(10);
        HBox lengthBox = new HBox(10);
        lengthBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        lengthSpinner = new Spinner<>(1, 1000, 16);
        lengthSpinner.setEditable(true);
        lengthBox.getChildren().addAll(new Label("Length:"), lengthSpinner);

        HBox optionsBox = new HBox(10);
        upperCheck = new CheckBox("Uppercase (A-Z)");
        upperCheck.setSelected(true);
        lowerCheck = new CheckBox("Lowercase (a-z)");
        lowerCheck.setSelected(true);
        digitsCheck = new CheckBox("Digits (0-9)");
        digitsCheck.setSelected(true);
        symbolsCheck = new CheckBox("Symbols (!@#...)");
        symbolsCheck.setSelected(false);
        optionsBox.getChildren().addAll(upperCheck, lowerCheck, digitsCheck, symbolsCheck);

        Button generateBtn = new Button("Generate");
        generateBtn.setOnAction(e -> generate());
        
        config.getChildren().addAll(lengthBox, optionsBox, generateBtn);

        outputArea = new CodeArea();
        outputArea.setPlaceholder(new Label("Generated string appears here..."));
        outputArea.getStyleClass().add("code-editor");
        outputArea.setEditable(false);
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, config, new VirtualizedScrollPane<>(outputArea), errorLabel);
    }

    private void generate() {
        try {
            String result = RandomUtil.generateRandomString(
                    lengthSpinner.getValue(),
                    upperCheck.isSelected(),
                    lowerCheck.isSelected(),
                    digitsCheck.isSelected(),
                    symbolsCheck.isSelected()
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
