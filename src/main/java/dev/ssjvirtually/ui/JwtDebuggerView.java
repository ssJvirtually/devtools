package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.JwtDebuggerUtil;
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

public class JwtDebuggerView extends VBox {

    private final CodeArea tokenArea;
    private final CodeArea detailsArea;
    private final Label errorLabel;

    public JwtDebuggerView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("JWT Debugger");
        title.getStyleClass().add("tool-title");

        Button decodeButton = new Button("Decode JWT");
        decodeButton.setOnAction(e -> decodeToken());
        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, decodeButton, copyButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);

        tokenArea = createEditor("Paste JWT token here...");
        detailsArea = createEditor("Decoded header, payload, and claims will appear here...");
        detailsArea.setEditable(false);

        splitPane.getItems().addAll(
                new VirtualizedScrollPane<>(tokenArea),
                new VirtualizedScrollPane<>(detailsArea)
        );

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, splitPane, errorLabel);
    }

    private CodeArea createEditor(String promptText) {
        CodeArea area = new CodeArea();
        area.setPlaceholder(new Label(promptText));
        area.getStyleClass().add("code-editor");
        return area;
    }

    private void decodeToken() {
        try {
            JwtDebuggerUtil.JwtParts parts = JwtDebuggerUtil.decode(tokenArea.getText());
            String result = """
                    Header:
                    %s

                    Payload:
                    %s

                    Signature:
                    %s

                    Time Claims:
                    %s
                    """.formatted(
                    parts.headerJson(),
                    parts.payloadJson(),
                    parts.signature(),
                    parts.claimsSummary()
            );
            detailsArea.replaceText(result.trim());
            clearError();
        } catch (Exception ex) {
            showError("Invalid JWT: " + ex.getMessage());
        }
    }

    private void copyToClipboard() {
        ClipboardContent content = new ClipboardContent();
        content.putString(detailsArea.getText());
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
