package com.irina.mindhaven.mindhavendesktop;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthenticationController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void handleAuthenticate() {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("Authenticate: " + email);

        try {
            boolean success = apiClient.authenticate(email, password);
            if (success)
                MainApplication.showDashboard();
            else
                showAlert("Login failed", "Invalid credentials");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToAuthenticate() throws Exception {
        MainApplication.showAuthenticate();
    }

    @FXML
    private void handleRegister() throws Exception {
        MainApplication.showAuthenticate();
    }

    @FXML
    private void handleLogout() throws Exception {
        try {
            apiClient.logout();
            MainApplication.showAuthenticate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() throws Exception {
        MainApplication.showRegister();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
