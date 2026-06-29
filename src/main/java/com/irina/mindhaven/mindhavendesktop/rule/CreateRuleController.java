package com.irina.mindhaven.mindhavendesktop.rule;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateRuleController {

    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<Integer> expiresComboBox;
    @FXML private TextField valueField;
    @FXML private Label descriptionLabel;

    @FXML private Spinner<Integer> hoursSpinner;
    @FXML private Spinner<Integer> minutesSpinner;
    @FXML private Spinner<Integer> startHourSpinner;
    @FXML private Spinner<Integer> startMinuteSpinner;
    @FXML private Spinner<Integer> endHourSpinner;
    @FXML private Spinner<Integer> endMinuteSpinner;

    @FXML private VBox categoryContainer;
    @FXML private VBox valueContainer;
    @FXML private VBox timeLimitContainer;
    @FXML private VBox timeWindowContainer;

    @FXML private Label globalErrorLabel;
    @FXML private Label categoryErrorLabel;
    @FXML private Label valueErrorLabel;
    @FXML private Label timeLimitErrorLabel;
    @FXML private Label timeWindowErrorLabel;
    @FXML private Label successLabel;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll("TIME_LIMIT", "TIME_WINDOW", "DOMAIN", "BLOCK_CATEGORY");
        categoryComboBox.getItems().addAll("SOCIAL_MEDIA", "GAMBLING", "GAMING", "SHOPPING", "PORN", "AI", "CHAT", "DATING", "DRUGS");

        for (int i = 1; i <= 30; i++)
            expiresComboBox.getItems().add(i);
        expiresComboBox.setValue(1);

        hoursSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 3, 0));
        minutesSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        startHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        startMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        endHourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        endMinuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        startHourSpinner.valueProperty().addListener((obs, oldV, newV) -> validateTimeWindowOrder());
        startMinuteSpinner.valueProperty().addListener((obs, oldV, newV) -> validateTimeWindowOrder());
        endHourSpinner.valueProperty().addListener((obs, oldV, newV) -> validateTimeWindowOrder());
        endMinuteSpinner.valueProperty().addListener((obs, oldV, newV) -> validateTimeWindowOrder());

        typeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldType, newType) -> {
            clearInlineErrors();
            updateFormVisibility(newType);
            updateDescriptionText(newType);
        });
        typeComboBox.setValue("DOMAIN");
    }

    private boolean validateTimeWindowOrder() {
        if (!"TIME_WINDOW".equals(typeComboBox.getValue()))
            return true;
        LocalTime start = LocalTime.of(startHourSpinner.getValue(), startMinuteSpinner.getValue());
        LocalTime end = LocalTime.of(endHourSpinner.getValue(), endMinuteSpinner.getValue());
        if (!start.isBefore(end)) {
            showInlineError(timeWindowErrorLabel, "End time must be strictly after start time!");
            return false;
        } else {
            hideInlineError(timeWindowErrorLabel);
            return true;
        }
    }

    private void updateFormVisibility(String type) {
        categoryContainer.setVisible("BLOCK_CATEGORY".equals(type));
        categoryContainer.setManaged("BLOCK_CATEGORY".equals(type));

        boolean needsValue = "DOMAIN".equals(type) || "TIME_WINDOW".equals(type) || "TIME_LIMIT".equals(type);
        valueContainer.setVisible(needsValue);
        valueContainer.setManaged(needsValue);

        timeLimitContainer.setVisible("TIME_LIMIT".equals(type));
        timeLimitContainer.setManaged("TIME_LIMIT".equals(type));

        timeWindowContainer.setVisible("TIME_WINDOW".equals(type));
        timeWindowContainer.setManaged("TIME_WINDOW".equals(type));
    }

    private void updateDescriptionText(String type) {
        if (type == null) {
            descriptionLabel.setText("");
            return;
        }
        switch(type) {
            case "DOMAIN":
                descriptionLabel.setText("DomainRule: This type of rule will block the access to the domain/app written in the \"Target (App/Site)\" field. The rule will be available for the period of time selected at \"Validity (Days)\" field. After the rule expires, it will be no longer available and the access to the domain/app will be possible again.");
                break;
            case "BLOCK_CATEGORY":
                descriptionLabel.setText("BlockCategoryRule: This type of rule will block the access to all the sites and apps from the category chosen. The rule will be available for the period of time selected at \"Validity (Days)\" field. After the rule expires, it will be no longer available and the access to all the apps and sites from the selected category will be possible again.");
                break;
            case "TIME_LIMIT":
                descriptionLabel.setText("TimeLimitRule: This type of rule will provide limited access to the domain/app written in the \"Target (App/Site)\" field. This limit will be set at the \"Allowed Time\" field (how much time per day to be allowed to access the domain/app). The rule will be available for the period of time selected at \"Validity (Days)\" field. After the rule expires, it will be no longer available and the daily access to the selected domain/app will be unlimited.");
                break;
            case "TIME_WINDOW":
                descriptionLabel.setText("TimeWindowRule: This type of rule will provide access to the domain/app written in the \"Target (App/Site)\" field between the selected time from the \"Start Time\" and \"End Time\" fields. The rule will be available for the period of time selected at \"Validity (Days)\" field. After the rule expires, it will be no longer available and the access to the selected domain/app will be possible at any time.");
                break;
            default:
                descriptionLabel.setText("");
                break;
        }
    }

    @FXML
    private void handleSaveRule() {
        clearInlineErrors();
        String selectedType = typeComboBox.getValue();
        boolean isValid = true;

        RuleDTO newRule = new RuleDTO();
        newRule.setUserUuid(SessionContext.getUserUuid());
        newRule.setType(selectedType);
        newRule.setActive(true);

        if ("BLOCK_CATEGORY".equals(selectedType)) {
            if (categoryComboBox.getValue() == null) {
                showInlineError(categoryErrorLabel, "Category is required!");
                isValid = false;
            }
            else
                newRule.setCategory(categoryComboBox.getValue());
        }
        else {
            String targetVal = valueField.getText();
            if (targetVal == null || targetVal.trim().isEmpty()) {
                showInlineError(valueErrorLabel, "Target resource is required!");
                isValid = false;
            }
            else
                newRule.setValue(targetVal.trim());
        }
        if ("TIME_LIMIT".equals(selectedType)) {
            int hours = hoursSpinner.getValue();
            int minutes = minutesSpinner.getValue();
            int totalMinutes = hours * 60 + minutes;
            if (totalMinutes == 0) {
                showInlineError(timeLimitErrorLabel, "Time limit is required!");
                isValid = false;
            }
            else
                newRule.setMaxMinutes(totalMinutes);
        }
        if ("TIME_WINDOW".equals(selectedType)) {
            if (!validateTimeWindowOrder())
                isValid = false;
            else {
                LocalTime start = LocalTime.of(startHourSpinner.getValue(), startMinuteSpinner.getValue());
                LocalTime end = LocalTime.of(endHourSpinner.getValue(), endMinuteSpinner.getValue());
                newRule.setStartTime(start);
                newRule.setEndTime(end);
            }
        }
        if (!isValid) {
            showInlineError(globalErrorLabel, "Error(s) detected in creating the rule!");
            return;
        }

        newRule.setExpiresAt(LocalDateTime.now().plusDays(expiresComboBox.getValue()));

        try {
            apiClient.createRule(newRule);
            successLabel.setText("Rule created successfully!");
            successLabel.setVisible(true);
            successLabel.setManaged(true);
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(() -> {
                        try {
                            handleBack();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearInlineErrors() {
        hideInlineError(globalErrorLabel);
        hideInlineError(categoryErrorLabel);
        hideInlineError(valueErrorLabel);
        hideInlineError(timeLimitErrorLabel);
        hideInlineError(timeWindowErrorLabel);
        hideInlineError(successLabel);
    }

    private void showInlineError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
    }

    private void hideInlineError(Label label) {
        label.setText("");
        label.setVisible(false);
        label.setManaged(false);
    }

    @FXML
    private void handleBack() throws Exception { MainApplication.showDashboard(); }
}
