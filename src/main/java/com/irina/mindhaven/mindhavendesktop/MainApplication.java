package com.irina.mindhaven.mindhavendesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage primaryStage;
    public static final ApiClient apiClient = new ApiClient();

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setWidth(500);
        primaryStage.setHeight(400);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        showAuthenticate();
    }

    public static void showAuthenticate() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/Authenticate.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Authenticate");
        primaryStage.show();
    }

    public static void showRegister() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/Register.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    public static void showDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/Dashboard.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}