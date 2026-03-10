package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        try {
            String data = apiClient.getDashboardData();
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openUsers() throws Exception {
        MainApplication.showUsers();
    }

    @FXML
    private void openLogs() throws Exception {
        MainApplication.showLogs();
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
}
