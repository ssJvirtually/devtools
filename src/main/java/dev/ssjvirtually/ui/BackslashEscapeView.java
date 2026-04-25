package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.EscapeUtil;
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

public class BackslashEscapeView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;

    public BackslashEscapeView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Backslash Escape/Unescape");
        title.getStyleClass().add("tool-title");
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox actions = new HBox(10);
        Button escapeButton = new Button("Escape");
        escapeButton.setOnAction(e -> escapeInput());
        Button unescapeButton = new Button("Unescape");
        unescapeButton.setOnAction(e -> unescapeInput());
        actions.getChildren().addAll(escapeButton, unescapeButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Enter text...");
        outputArea = createEditor("Escaped/unescaped output...");
        outputArea.setEditable(false);
        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                new VirtualizedScrollPane<>(outputArea)
        );

        getChildren().addAll(header, actions, splitPane);
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

    private void escapeInput() {
        outputArea.replaceText(EscapeUtil.escapeBackslashes(inputArea.getText()));
    }

    private void unescapeInput() {
        outputArea.replaceText(EscapeUtil.unescapeBackslashes(inputArea.getText()));
    }
}
