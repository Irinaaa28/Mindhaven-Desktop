package com.irina.mindhaven.mindhavendesktop;

import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        showHome();
    }

    public static void showHome() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/Home.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("MindHaven");
        primaryStage.show();
    }

    public static void showAuthenticate() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/Authenticate.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Authenticate");
    }

    public static void showRegister() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/Register.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register");
    }

    public static void showForgotPassword() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/ForgotPassword.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Forgot password");
    }

    public static void showDashboard() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/Dashboard.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dashboard");
    }

    public static void showUsers() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/Users.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Users");
    }

    public static void showLogs() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("/com/irina/mindhaven/mindhavendesktop/view/Logs.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Logs");
    }

    public static void main(String[] args) {
        launch();
    }
}