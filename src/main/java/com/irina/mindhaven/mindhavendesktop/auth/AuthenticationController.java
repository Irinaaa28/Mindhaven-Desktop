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
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label lockLabel;
    @FXML private Button loginButton;
    @FXML private Label authErrorLabel;
    private Timeline lockTimeline;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    private void handleAuthenticate() {
        String email = emailField.getText();
        String password = passwordField.getText();

        authErrorLabel.setVisible(false);
        authErrorLabel.setManaged(false);
        if (email.isEmpty() || password.isEmpty()) {
            authErrorLabel.setText("Email and password are required!");
            authErrorLabel.setVisible(true);
            authErrorLabel.setManaged(true);
            return;
        }
        try {
            boolean success = apiClient.authenticate(email, password);
            if (success) {
                if (MainApplication.localSessionServer != null)
                    MainApplication.localSessionServer.stop();
                MainApplication.localSessionServer = new LocalSessionServer();
                MainApplication.localSessionServer.start();
                var user = apiClient.getCurrentUser();
                SessionContext.setUserUuid(user.getUuid());
                MainApplication.getActivityScheduler().start();
                MainApplication.showDashboard();
            }
            else {
                authErrorLabel.setText("Invalid credentials!");
                authErrorLabel.setVisible(true);
                authErrorLabel.setManaged(true);
            }
        } catch (AccountLockedException e) {
            startLockCountdown(e.getTtlSeconds());
        }
        catch (Exception e) {
            authErrorLabel.setText("Error: " + e.getMessage());
            authErrorLabel.setVisible(true);
            authErrorLabel.setManaged(true);
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