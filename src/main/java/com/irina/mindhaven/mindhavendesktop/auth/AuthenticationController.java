package com.irina.mindhaven.mindhavendesktop.auth;

import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.rule.RuleDTO;
import com.irina.mindhaven.mindhavendesktop.session.LocalSessionServer;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

public class AuthenticationController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label lockLabel;
    @FXML
    private Button loginButton;
    private Timeline lockTimeline;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void handleAuthenticate() {
        String email = emailField.getText();
        String password = passwordField.getText();
        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Email and password are required");
            return;
        }
        try {
            boolean success = apiClient.authenticate(email, password);
            if (success) {
                LocalSessionServer server = new LocalSessionServer();
                server.start();
                var user = apiClient.getCurrentUser();
                SessionContext.setUserUuid(user.getUuid());
                MainApplication.getActivityScheduler().start();
                MainApplication.showDashboard();
            }
            else
                showAlert("Login failed", "Invalid credentials");
        } catch (AccountLockedException e) {
            startLockCountdown(e.getTtlSeconds());
        }
        catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void goToRegister() throws Exception {
        MainApplication.showRegister();
    }

    @FXML
    private void goToForgotPassword() throws Exception {
        MainApplication.showForgotPassword();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void startLockCountdown(long ttlSeconds) {
        loginButton.setDisable(true);
        lockLabel.setVisible(true);
        updateCountdownLabel(ttlSeconds);
        if (lockTimeline != null)
            lockTimeline.stop();
        final long[] remaining = {ttlSeconds};
        lockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remaining[0]--;
            if (remaining[0] <= 0) {
                lockTimeline.stop();
                loginButton.setDisable(false);
                lockLabel.setVisible(false);
            } else {
                updateCountdownLabel(remaining[0]);
            }
        }));
        lockTimeline.setCycleCount(Animation.INDEFINITE);
        lockTimeline.play();
    }

    private void updateCountdownLabel(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        String text;
        if (hours > 0) {
            text = String.format("Account blocked. Try again after %d:%02d:%02d",
                    hours, minutes, secs);
        } else {
            text = String.format("Account blocked. Try again after %02d:%02d",
                    minutes, secs);
        }
        lockLabel.setText(text);
    }
}
