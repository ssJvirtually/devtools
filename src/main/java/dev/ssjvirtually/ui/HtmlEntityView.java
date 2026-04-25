package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.HtmlEntityUtil;
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

public class HtmlEntityView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;

    public HtmlEntityView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("HTML Entity Encode/Decode");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        inputArea = createEditor("Enter text...");
        outputArea = createEditor("Result appears here...");
        outputArea.setEditable(false);

        HBox config = new HBox(10);
        Button encodeBtn = new Button("Encode");
        encodeBtn.setOnAction(e -> outputArea.replaceText(HtmlEntityUtil.encode(inputArea.getText())));
        Button decodeBtn = new Button("Decode");
        decodeBtn.setOnAction(e -> outputArea.replaceText(HtmlEntityUtil.decode(inputArea.getText())));
        config.getChildren().addAll(encodeBtn, decodeBtn);

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
