package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.MarkdownUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

public class MarkdownPreviewView extends VBox {

    private final CodeArea inputArea;
    private final WebView webView;

    public MarkdownPreviewView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Markdown Preview");
        title.getStyleClass().add("tool-title");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Button refreshBtn = new Button("Refresh Preview");
        refreshBtn.setOnAction(e -> updatePreview());
        
        header.getChildren().addAll(title, spacer, refreshBtn);

        inputArea = new CodeArea();
        inputArea.setPlaceholder(new Label("Enter Markdown to preview..."));
        inputArea.getStyleClass().add("code-editor");
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> updatePreview());

        webView = new WebView();
        VBox.setVgrow(webView, Priority.ALWAYS);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(inputArea),
                webView
        );

        getChildren().addAll(header, splitPane);
    }

    private void updatePreview() {
        String markdown = inputArea.getText();
        String html = MarkdownUtil.toHtml(markdown);
        // Add basic styling for the preview
        String styledHtml = "<html><body style='font-family: sans-serif; padding: 20px; line-height: 1.6;'>" + html + "</body></html>";
        webView.getEngine().loadContent(styledHtml);
    }
}
