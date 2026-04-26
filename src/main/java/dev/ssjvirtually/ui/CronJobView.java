package dev.ssjvirtually.ui;

import com.cronutils.model.CronType;
import dev.ssjvirtually.util.CronUtil;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

public class CronJobView extends VBox {

    private final TextField cronField;
    private final ComboBox<CronType> typeComboBox;
    private final TextArea descriptionArea;
    private final ListView<String> nextExecutionsList;
    private final Label errorLabel;

    public CronJobView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Cron Job Parser");
        title.getStyleClass().add("tool-title");
        header.getChildren().add(title);

        HBox config = new HBox(10);
        cronField = new TextField();
        cronField.setPromptText("Enter cron expression (e.g. */5 * * * *)");
        HBox.setHgrow(cronField, Priority.ALWAYS);
        cronField.textProperty().addListener((obs, oldVal, newVal) -> parseCron());

        typeComboBox = new ComboBox<>(FXCollections.observableArrayList(
                CronType.QUARTZ, CronType.UNIX, CronType.CRON4J, CronType.SPRING
        ));
        typeComboBox.setValue(CronType.UNIX);
        typeComboBox.setOnAction(e -> parseCron());

        config.getChildren().addAll(cronField, typeComboBox);

        VBox content = new VBox(10);
        VBox.setVgrow(content, Priority.ALWAYS);

        Label descLabel = new Label("Description:");
        descriptionArea = new TextArea();
        descriptionArea.setEditable(false);
        descriptionArea.setPrefHeight(100);
        descriptionArea.setWrapText(true);

        Label nextLabel = new Label("Next Executions:");
        nextExecutionsList = new ListView<>();
        VBox.setVgrow(nextExecutionsList, Priority.ALWAYS);

        content.getChildren().addAll(descLabel, descriptionArea, nextLabel, nextExecutionsList);

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, config, content, errorLabel);
    }

    private void parseCron() {
        String cron = cronField.getText();
        CronType type = typeComboBox.getValue();

        if (cron == null || cron.trim().isEmpty()) {
            descriptionArea.setText("");
            nextExecutionsList.getItems().clear();
            clearError();
            return;
        }

        try {
            String description = CronUtil.describe(cron, type);
            descriptionArea.setText(description);

            List<String> nextExecutions = CronUtil.getNextExecutions(cron, type, 10);
            nextExecutionsList.setItems(FXCollections.observableArrayList(nextExecutions));

            clearError();
        } catch (Exception ex) {
            descriptionArea.setText("");
            nextExecutionsList.getItems().clear();
            showError(ex.getMessage());
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
