package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.StringUtil;
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

public class StringCaseConverterView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final ComboBox<String> caseCombo;

    public StringCaseConverterView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("String Case Converter");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox config = new HBox(10);
        caseCombo = new ComboBox<>();
        caseCombo.getItems().addAll(
                "camelCase",
                "PascalCase",
                "snake_case",
                "kebab-case",
                "CONSTANT_CASE",
                "Title Case",
                "Sentence case",
                "lowercase",
                "UPPERCASE"
        );
        caseCombo.setValue("camelCase");
        caseCombo.setOnAction(e -> convert());
        config.getChildren().addAll(new Label("Target Case:"), caseCombo);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Enter text to convert...");
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> convert());
        
        outputArea = createEditor("Converted text appears here...");
        outputArea.setEditable(false);
        
        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                new VirtualizedScrollPane<>(outputArea)
        );

        getChildren().addAll(header, config, splitPane);
    }

    private CodeArea createEditor(String placeholder) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(placeholder));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void convert() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            outputArea.replaceText("");
            return;
        }

        String targetCase = caseCombo.getValue();
        String result;
        switch (targetCase) {
            case "camelCase":
                result = StringUtil.toCamelCase(input);
                break;
            case "PascalCase":
                result = StringUtil.toPascalCase(input);
                break;
            case "snake_case":
                result = StringUtil.toSnakeCase(input);
                break;
            case "kebab-case":
                result = StringUtil.toKebabCase(input);
                break;
            case "CONSTANT_CASE":
                result = StringUtil.toConstantCase(input);
                break;
            case "Title Case":
                result = StringUtil.toTitleCase(input);
                break;
            case "Sentence case":
                result = StringUtil.toSentenceCase(input);
                break;
            case "lowercase":
                result = input.toLowerCase();
                break;
            case "UPPERCASE":
                result = input.toUpperCase();
                break;
            default:
                result = input;
        }
        outputArea.replaceText(result);
    }

    private void copyToClipboard() {
        ClipboardContent content = new ClipboardContent();
        content.putString(outputArea.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}
