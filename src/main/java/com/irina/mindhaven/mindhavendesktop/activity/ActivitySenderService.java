package com.irina.mindhaven.mindhavendesktop.activity;

import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.rule.RuleDecision;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class ActivitySenderService {

    private final ApiClient apiClient = new ApiClient();

    public boolean send(ActivityEvent event) {
        if (event == null || event.getUserUuid() == null || event.getUserUuid().isBlank())
            return false;
        try {
            RuleDecision decision = apiClient.sendActivity(event);
            if (decision != null && decision.isBlocked()) {
                if (event.getDurationSeconds() == 0 || event.getDurationSeconds() >= 2)
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("MindHaven - Active Restriction");
                        alert.setHeaderText(null);
                        alert.setContentText(decision.getReason());
                        alert.showAndWait();
                    });
                String appName = event.getApplicationName();
                if (appName != null && !appName.equalsIgnoreCase("Unknown"))
                    Runtime.getRuntime().exec("taskkill /F /IM " + appName);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
