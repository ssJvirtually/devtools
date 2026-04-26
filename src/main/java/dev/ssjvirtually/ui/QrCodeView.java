package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.QrCodeUtil;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;

public class QrCodeView extends VBox {

    private final TextField inputField;
    private final ImageView qrImageView;
    private final TextArea readResultArea;
    private final Label errorLabel;

    public QrCodeView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("QR Code Generator / Reader");
        title.getStyleClass().add("tool-title");
        header.getChildren().add(title);

        TabPane tabPane = new TabPane();
        VBox.setVgrow(tabPane, Priority.ALWAYS);

        // Generator Tab
        Tab generateTab = new Tab("Generator");
        generateTab.setClosable(false);
        VBox genContent = new VBox(10);
        genContent.setPadding(new Insets(10));

        inputField = new TextField();
        inputField.setPromptText("Enter text or URL to generate QR code");
        inputField.textProperty().addListener((obs, oldVal, newVal) -> generateQr());

        qrImageView = new ImageView();
        qrImageView.setFitWidth(300);
        qrImageView.setFitHeight(300);
        qrImageView.setPreserveRatio(true);
        
        ScrollPane scrollPane = new ScrollPane(qrImageView);
        scrollPane.getStyleClass().add("image-preview");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        genContent.getChildren().addAll(new Label("Text:"), inputField, scrollPane);
        generateTab.setContent(genContent);

        // Reader Tab
        Tab readTab = new Tab("Reader");
        readTab.setClosable(false);
        VBox readContent = new VBox(10);
        readContent.setPadding(new Insets(10));

        Button uploadButton = new Button("Upload QR Code Image");
        uploadButton.setOnAction(e -> uploadAndRead());

        readResultArea = new TextArea();
        readResultArea.setPromptText("Decoded text will appear here...");
        readResultArea.setEditable(false);
        VBox.setVgrow(readResultArea, Priority.ALWAYS);

        readContent.getChildren().addAll(uploadButton, new Label("Result:"), readResultArea);
        readTab.setContent(readContent);

        tabPane.getTabs().addAll(generateTab, readTab);

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, tabPane, errorLabel);
    }

    private void generateQr() {
        String text = inputField.getText();
        if (text == null || text.trim().isEmpty()) {
            qrImageView.setImage(null);
            clearError();
            return;
        }

        try {
            byte[] bytes = QrCodeUtil.generateQrCode(text, 500, 500);
            qrImageView.setImage(new Image(new ByteArrayInputStream(bytes)));
            clearError();
        } catch (Exception ex) {
            qrImageView.setImage(null);
            showError("Failed to generate QR code: " + ex.getMessage());
        }
    }

    private void uploadAndRead() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open QR Code Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        File selectedFile = fileChooser.showOpenDialog(getScene().getWindow());

        if (selectedFile != null) {
            try {
                byte[] bytes = Files.readAllBytes(selectedFile.toPath());
                String result = QrCodeUtil.readQrCode(bytes);
                readResultArea.setText(result);
                clearError();
            } catch (Exception ex) {
                readResultArea.setText("");
                showError("Failed to read QR code: " + ex.getMessage());
            }
        }
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
