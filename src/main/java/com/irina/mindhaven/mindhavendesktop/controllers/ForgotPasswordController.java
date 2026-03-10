package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ForgotPasswordController {
    @FXML
    private TextField emailField;
    @FXML
    private Label messageLabel;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void sendResetLink() {
        String email = emailField.getText();
        if (email.isEmpty()) {
            messageLabel.setText("Email is required");
            return;
        }
        try {
            apiClient.forgotPassword(email);
            messageLabel.setText("Check your email for reset link");
        } catch(Exception e) {
            messageLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void goToAuthenticate() throws Exception {
        MainApplication.showAuthenticate();
    }
}
