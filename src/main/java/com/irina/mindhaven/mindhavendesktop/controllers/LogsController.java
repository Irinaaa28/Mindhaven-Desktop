package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.models.LogDTO;
import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.util.List;

public class LogsController {
    @FXML
    private TableView<LogDTO> logsTable;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void initialize() {
        try {
            List<LogDTO> logs = apiClient.getLogs();
            logsTable.getItems().addAll(logs);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() throws Exception {
        MainApplication.showDashboard();
    }
}
