package com.irina.mindhaven.mindhavendesktop.home;

import com.irina.mindhaven.mindhavendesktop.session.SessionContext;
import com.irina.mindhaven.mindhavendesktop.user.UserDTO;
import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML private Button usersButton;
    @FXML private Button logsButton;
    @FXML private Button rulesButton;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML private Label welcomeLabel;
    @FXML private Label loginSuccessLabel;

    private static boolean showSuccessBanner = true;

    @FXML
    private void initialize() {
        try {
            UserDTO user = apiClient.getCurrentUser();
            SessionContext.setUserUuid(user.getUuid());
            if (!user.getRole().contains("ADMIN")) {
                usersButton.setVisible(false);
                usersButton.setManaged(false);
                if (rulesButton != null) {
                    rulesButton.setVisible(false);
                    rulesButton.setManaged(false);
                }
            } else {
                if (rulesButton != null) {
                    rulesButton.setVisible(true);
                    rulesButton.setManaged(true);
                }
                if (usersButton != null) {
                    usersButton.setVisible(true);
                    usersButton.setManaged(true);
                }
                if (logsButton != null) {
                    logsButton.setVisible(true);
                    logsButton.setManaged(true);
                }
            }
            if (showSuccessBanner) {
                loginSuccessLabel.setText("Authentication made successfully!");
                loginSuccessLabel.setVisible(true);
                loginSuccessLabel.setManaged(true);
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        Platform.runLater(() -> {
                            loginSuccessLabel.setVisible(false);
                            loginSuccessLabel.setManaged(false);
                        });
                        showSuccessBanner = false;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
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
            if (MainApplication.localSessionServer != null) {
                MainApplication.localSessionServer.stop();
                MainApplication.localSessionServer = null;
            }
            showSuccessBanner = true;
            MainApplication.showAuthenticate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateRule() throws Exception {
        MainApplication.showCreateRule();
    }

    @FXML
    private void handleMyRules() throws Exception {
        MainApplication.showMyRules();

    }

    @FXML
    private void openRules() throws Exception {
        MainApplication.showAllRules();
    }
}
