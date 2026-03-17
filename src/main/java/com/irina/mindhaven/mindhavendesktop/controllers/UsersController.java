package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.models.UserDTO;
import com.irina.mindhaven.mindhavendesktop.services.ApiClient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class UsersController {
    @FXML
    private TableView<UserDTO> usersTable;
    @FXML
    private TableColumn<UserDTO, String> uuidColumn;
    @FXML
    private TableColumn<UserDTO, String> firstnameColumn;
    @FXML
    private TableColumn<UserDTO, String> lastnameColumn;
    @FXML
    private TableColumn<UserDTO, String> emailColumn;
    @FXML
    private TableColumn<UserDTO, String> roleColumn;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void initialize() {
        uuidColumn.setCellValueFactory(new PropertyValueFactory<>("uuid"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUsers();
    }

    private void loadUsers() {
        try {
            List<UserDTO> users = apiClient.getUsers();
            System.out.println("Users loaded: " + users.size());
            usersTable.setItems(FXCollections.observableArrayList(users));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() throws Exception {
        MainApplication.showDashboard();
    }
}
