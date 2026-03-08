package com.irina.mindhaven.mindhavendesktop;

import javafx.fxml.FXML;

public class DashboardController {

    private final ApiClient apiClient = MainApplication.apiClient;
    @FXML
    private void initialize() {
        try {
            String data = apiClient.getDashboardData();
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
