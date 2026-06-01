package com.irina.mindhaven.mindhavendesktop.home;

import com.irina.mindhaven.mindhavendesktop.session.SessionContext;
import com.irina.mindhaven.mindhavendesktop.user.UserDTO;
import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Button usersButton;
    @FXML
    private Button logsButton;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private Label welcomeLabel;

    @FXML
    private void initialize() {
        try {
            UserDTO user = apiClient.getCurrentUser();
            SessionContext.setUserUuid(user.getUuid());
            if (!user.getRole().equals("ADMIN")) {
                usersButton.setVisible(false);
                logsButton.setVisible(false);
            }
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
