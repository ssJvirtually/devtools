package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.IdentifierUtil;
import dev.ssjvirtually.util.UnixTimeConverterUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UuidUlidView extends VBox {

    private final TextField uuidField;
    private final TextField ulidField;
    private final TextField decodedTimeField;
    private final Label errorLabel;

    public UuidUlidView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("UUID/ULID Generate/Decode");
        title.getStyleClass().add("tool-title");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        uuidField = new TextField();
        uuidField.setPromptText("Generated UUID");
        ulidField = new TextField();
        ulidField.setPromptText("Generated or input ULID");
        decodedTimeField = new TextField();
        decodedTimeField.setEditable(false);
        decodedTimeField.setPromptText("Decoded ULID timestamp");

        Button generateUuid = new Button("Generate UUID");
        generateUuid.setOnAction(e -> uuidField.setText(IdentifierUtil.generateUuid()));
        Button copyUuid = new Button("Copy");
        copyUuid.setOnAction(e -> copyText(uuidField.getText()));

        Button generateUlid = new Button("Generate ULID");
        generateUlid.setOnAction(e -> ulidField.setText(IdentifierUtil.generateUlid()));
        Button decodeUlid = new Button("Decode ULID Time");
        decodeUlid.setOnAction(e -> decodeUlid());
        Button copyUlid = new Button("Copy");
        copyUlid.setOnAction(e -> copyText(ulidField.getText()));

        grid.add(new Label("UUID"), 0, 0);
        grid.add(uuidField, 1, 0);
        grid.add(generateUuid, 2, 0);
        grid.add(copyUuid, 3, 0);

        grid.add(new Label("ULID"), 0, 1);
        grid.add(ulidField, 1, 1);
        grid.add(generateUlid, 2, 1);
        grid.add(copyUlid, 3, 1);

        grid.add(new Label("ULID Time"), 0, 2);
        grid.add(decodedTimeField, 1, 2);
        grid.add(decodeUlid, 2, 2);

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, grid, errorLabel);
    }

    private void decodeUlid() {
        try {
            long millis = IdentifierUtil.decodeUlidTimestamp(ulidField.getText());
            long epochSeconds = millis / 1000;
            decodedTimeField.setText(UnixTimeConverterUtil.unixToLocalDateTimeString(epochSeconds) + " (" + millis + " ms)");
            clearError();
        } catch (Exception ex) {
            showError("Invalid ULID: " + ex.getMessage());
        }
    }

    private void copyText(String text) {
        ClipboardContent content = new ClipboardContent();
        content.putString(text == null ? "" : text);
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
