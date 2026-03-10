package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
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
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Email and password are required");
            return;
        }
        try {
            boolean success = apiClient.authenticate(email, password);
            if (success)
                MainApplication.showDashboard();
            else
                showAlert("Login failed", "Invalid credentials");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void goToRegister() throws Exception {
        MainApplication.showRegister();
    }

    @FXML
    private void goToForgotPassword() throws Exception {
        MainApplication.showForgotPassword();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
