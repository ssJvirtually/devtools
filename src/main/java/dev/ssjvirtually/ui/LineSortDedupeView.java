package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.StringUtil;
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

public class LineSortDedupeView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;

    public LineSortDedupeView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Line Sort / Dedupe");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        inputArea = createEditor("Enter lines...");
        outputArea = createEditor("Result appears here...");
        outputArea.setEditable(false);

        HBox config = new HBox(10);
        Button sortAsc = new Button("Sort Ascending");
        sortAsc.setOnAction(e -> outputArea.replaceText(StringUtil.sortLines(inputArea.getText(), true)));
        Button sortDesc = new Button("Sort Descending");
        sortDesc.setOnAction(e -> outputArea.replaceText(StringUtil.sortLines(inputArea.getText(), false)));
        Button reverse = new Button("Reverse");
        reverse.setOnAction(e -> outputArea.replaceText(StringUtil.reverseLines(inputArea.getText())));
        Button dedupe = new Button("Deduplicate");
        dedupe.setOnAction(e -> outputArea.replaceText(StringUtil.deduplicateLines(inputArea.getText())));
        
        config.getChildren().addAll(sortAsc, sortDesc, reverse, dedupe);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        
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

    private void copyToClipboard() {
        ClipboardContent content = new ClipboardContent();
        content.putString(outputArea.getText());
        Clipboard.getSystemClipboard().setContent(content);
    }
}
