package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.LoremIpsumUtil;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class LoremIpsumView extends VBox {

    private final CodeArea outputArea;
    private final Spinner<Integer> countSpinner;
    private final ComboBox<String> typeCombo;

    public LoremIpsumView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Lorem Ipsum Generator");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox config = new HBox(10);
        config.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        countSpinner = new Spinner<>(1, 1000, 3);
        countSpinner.setEditable(true);
        
        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Words", "Sentences", "Paragraphs");
        typeCombo.setValue("Paragraphs");
        
        Button generateBtn = new Button("Generate");
        generateBtn.setOnAction(e -> generate());
        
        config.getChildren().addAll(
                new Label("Count:"), countSpinner,
                new Label("Type:"), typeCombo,
                generateBtn
        );

        outputArea = new CodeArea();
        outputArea.setPlaceholder(new Label("Generated text appears here..."));
        outputArea.getStyleClass().add("code-editor");
        outputArea.setEditable(false);
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        getChildren().addAll(header, config, new VirtualizedScrollPane<>(outputArea));
        
        // Generate initial content
        generate();
    }

    private void generate() {
        String result = LoremIpsumUtil.generate(countSpinner.getValue(), typeCombo.getValue());
        outputArea.replaceText(result);
    }

    private void copyToClipboard() {
        ClipboardContent content = new ClipboardContent();
        content.putString(outputArea.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}
