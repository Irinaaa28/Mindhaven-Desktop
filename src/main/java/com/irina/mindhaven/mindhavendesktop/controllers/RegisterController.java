package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static com.irina.mindhaven.mindhavendesktop.MainApplication.apiClient;

public class RegisterController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void register() {
        if (firstNameField.getText().isEmpty()) {
            errorLabel.setText("First name is required");
            return;
        }
        if (passwordField.getText().equals(confirmPasswordField.getText()) == false) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        try {
            boolean ok = apiClient.register(
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    passwordField.getText(),
                    confirmPasswordField.getText()
            );
            if (ok)
                MainApplication.showAuthenticate();
        } catch(Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    private void goToAuthenticate() throws Exception {
        MainApplication.showAuthenticate();
    }
}
