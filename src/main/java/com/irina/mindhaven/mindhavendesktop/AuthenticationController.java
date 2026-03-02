package com.irina.mindhaven.mindhavendesktop;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthenticationController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleAuthenticate() {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("Authenticate: " + email);

        try {
            MainApplication.showDashboard();
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
        MainApplication.showAuthenticate();
    }

    @FXML
    private void goToRegister() throws Exception {
        MainApplication.showRegister();
    }
}
