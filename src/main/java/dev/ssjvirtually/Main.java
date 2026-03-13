package dev.ssjvirtually;

import dev.ssjvirtually.ui.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainLayout root = new MainLayout();
        Scene scene = new Scene(root, 1024, 768);
        
        // Load custom CSS
        scene.getStylesheets().add(getClass().getResource("/styles/theme.css").toExternalForm());

        primaryStage.setTitle("DevUtils");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}