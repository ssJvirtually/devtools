package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.CertificateUtil;
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

public class CertificateDecoderView extends VBox {

    private final CodeArea inputArea;
    private final CodeArea outputArea;
    private final Label errorLabel;

    public CertificateDecoderView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Certificate Decoder (X.509)");
        title.getStyleClass().add("tool-title");

        Button copyButton = new Button("Copy Output");
        copyButton.setOnAction(e -> copyToClipboard());

        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, copyButton);

        HBox actions = new HBox(10);
        Button decodeButton = new Button("Decode Certificate");
        decodeButton.setOnAction(e -> decode());
        actions.getChildren().add(decodeButton);

        SplitPane splitPane = new SplitPane();
        VBox.setVgrow(splitPane, Priority.ALWAYS);
        inputArea = createEditor("Paste PEM encoded certificate here...");
        outputArea = createEditor("Decoded details appear here...");
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

    private void decode() {
        String input = inputArea.getText();
        if (input == null || input.isEmpty()) {
            showError("Input is empty");
            return;
        }
        try {
            outputArea.replaceText(CertificateUtil.decodeCertificate(input));
            clearError();
        } catch (Exception ex) {
            showError("Invalid certificate: " + ex.getMessage());
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
