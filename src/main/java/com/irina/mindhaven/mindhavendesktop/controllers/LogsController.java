package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.models.LogDTO;
import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class LogsController {
    @FXML
    private TableView<LogDTO> logsTable;
    @FXML
    private TableColumn<LogDTO, Long> idColumn;
    @FXML
    private TableColumn<LogDTO, String> emailColumn;
    @FXML
    private TableColumn<LogDTO, String> actionColumn;
    @FXML
    private TableColumn<LogDTO, String> ipColumn;
    @FXML
    private TableColumn<LogDTO, String> userAgentColumn;
    @FXML
    private TableColumn<LogDTO, String> timestampColumn;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        actionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        ipColumn.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
        userAgentColumn.setCellValueFactory(new PropertyValueFactory<>("userAgent"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        loadLogs();
    }

    private void loadLogs() {
        try {
            List<LogDTO> logs = apiClient.getLogs();
            System.out.println("Logs loaded: " + logs.size());
            logsTable.setItems(FXCollections.observableArrayList(logs));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() throws Exception {
        MainApplication.showDashboard();
    }
}
