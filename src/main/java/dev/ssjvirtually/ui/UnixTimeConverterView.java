package dev.ssjvirtually.ui;

import dev.ssjvirtually.util.UnixTimeConverterUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UnixTimeConverterView extends VBox {

    private final TextField unixInput;
    private final TextField dateTimeInput;
    private final Label errorLabel;

    public UnixTimeConverterView() {
        setPadding(new Insets(10));
        setSpacing(10);
        getStyleClass().add("main-workspace");

        HBox header = new HBox(10);
        Label title = new Label("Unix Time Converter");
        title.getStyleClass().add("tool-title");
        HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        unixInput = new TextField();
        unixInput.setPromptText("Unix seconds, e.g. 1714022400");

        dateTimeInput = new TextField();
        dateTimeInput.setPromptText("yyyy-MM-dd HH:mm:ss");

        Button unixToDateButton = new Button("Unix -> DateTime");
        unixToDateButton.setOnAction(e -> convertUnixToDateTime());
        Button dateToUnixButton = new Button("DateTime -> Unix");
        dateToUnixButton.setOnAction(e -> convertDateTimeToUnix());

        grid.add(new Label("Unix Seconds"), 0, 0);
        grid.add(unixInput, 1, 0);
        grid.add(unixToDateButton, 2, 0);

        grid.add(new Label("Local DateTime"), 0, 1);
        grid.add(dateTimeInput, 1, 1);
        grid.add(dateToUnixButton, 2, 1);

        errorLabel = new Label();
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        getChildren().addAll(header, grid, errorLabel);
    }

    private void convertUnixToDateTime() {
        try {
            long unix = Long.parseLong(unixInput.getText().trim());
            dateTimeInput.setText(UnixTimeConverterUtil.unixToLocalDateTimeString(unix));
            clearError();
        } catch (Exception ex) {
            showError("Invalid unix timestamp. Use whole seconds.");
        }
    }

    private void convertDateTimeToUnix() {
        try {
            long unix = UnixTimeConverterUtil.localDateTimeToUnix(dateTimeInput.getText().trim());
            unixInput.setText(Long.toString(unix));
            clearError();
        } catch (Exception ex) {
            showError("Invalid datetime. Expected format: yyyy-MM-dd HH:mm:ss");
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
