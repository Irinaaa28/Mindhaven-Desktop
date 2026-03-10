package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.models.UserDTO;
import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class UsersController {
    @FXML
    private TableView<UserDTO> usersTable;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void initialize() {
        try {
            List<UserDTO> users = apiClient.getUsers();
            usersTable.getItems().addAll(users);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void goBack() throws Exception {
        MainApplication.showDashboard();
    }
}
