package com.irina.mindhaven.mindhavendesktop.rule;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;

public class AllRulesController {

    @FXML private TableView<RuleDTO> allRulesTable;
    @FXML private TableColumn<RuleDTO, Long> idColumn;
    @FXML private TableColumn<RuleDTO, String> userColumn;
    @FXML private TableColumn<RuleDTO, String> typeColumn;
    @FXML private TableColumn<RuleDTO, String> valueColumn;

    @FXML private Label detailId;
    @FXML private Label detailUserUuid;
    @FXML private Label detailType;
    @FXML private Label detailCategory;
    @FXML private Label detailValue;
    @FXML private Label detailMaxMinutes;
    @FXML private Label detailTimeWindow;
    @FXML private Label detailExpiresAt;
    @FXML private Label detailActive;

    @FXML private HBox rowCategory;
    @FXML private HBox rowValue;
    @FXML private HBox rowMaxMinutes;
    @FXML private HBox rowTimeWindow;
    @FXML private HBox rowExpiresAt;

    @FXML private Button deleteRuleButton;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userColumn.setCellValueFactory(new PropertyValueFactory<>("userUuid"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        loadUserRules();

        allRulesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateDetails(newSelection);
                deleteRuleButton.setDisable(false);
            }
            else
                deleteRuleButton.setDisable(true);
        });
    }

    private void loadUserRules() {
        try {
            List<RuleDTO> rules = apiClient.getAllRules();
            allRulesTable.setItems(FXCollections.observableArrayList(rules));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleViewDetails() {
        RuleDTO selectedRule = allRulesTable.getSelectionModel().getSelectedItem();
        if (selectedRule == null) {
            new Alert(Alert.AlertType.WARNING, "Select a rule", ButtonType.OK).showAndWait();
            return;
        }
        populateDetails(selectedRule);
    }

    private void populateDetails(RuleDTO rule) {
        detailId.setText(String.valueOf(rule.getId()));
        detailUserUuid.setText(rule.getUserUuid());
        detailType.setText(rule.getType());
        detailActive.setText(rule.isActive() ? "YES" : "NO (Expired");

        if (rule.getValue() != null) {
            detailValue.setText(rule.getValue());
            toggleRow(rowValue, true);
        }
        else
            toggleRow(rowValue, false);

        if (rule.getCategory() != null) {
            detailCategory.setText(rule.getCategory());
            toggleRow(rowCategory, true);
        }
        else
            toggleRow(rowCategory, false);

        if (rule.getMaxMinutes() != null) {
            detailMaxMinutes.setText(rule.getMaxMinutes() + " minutes");
            toggleRow(rowMaxMinutes, true);
        }
        else
            toggleRow(rowMaxMinutes, false);

        if (rule.getStartTime() != null && rule.getEndTime() != null) {
            detailTimeWindow.setText(rule.getStartTime() + " - " + rule.getEndTime());
            toggleRow(rowTimeWindow, true);
        }
        else
            toggleRow(rowTimeWindow, false);

        if (rule.getExpiresAt() != null) {
            detailExpiresAt.setText(rule.getExpiresAt().toString());
            toggleRow(rowExpiresAt, true);
        }
        else
            toggleRow(rowExpiresAt, false);
    }

    @FXML
    private void handleDeleteRule() {
        RuleDTO selectedRule = allRulesTable.getSelectionModel().getSelectedItem();
        if (selectedRule == null)
            return;
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this rule?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait();
        if (confirmation.getResult() == ButtonType.YES) {
            try {
                apiClient.deleteRule(selectedRule.getId());
                clearDetailsPane();
                loadUserRules();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Could not delete rule: " + e.getMessage(), ButtonType.OK).showAndWait();
            }
        }
    }

    private void clearDetailsPane() {
        detailId.setText("");
        detailUserUuid.setText("");
        detailType.setText("");
        detailValue.setText("");
        detailActive.setText("");
        deleteRuleButton.setDisable(true);
        toggleRow(rowCategory, false);
        toggleRow(rowMaxMinutes, false);
        toggleRow(rowTimeWindow, false);
        toggleRow(rowExpiresAt, false);
    }

    private void toggleRow(HBox row, boolean isVisible) {
        row.setVisible(isVisible);
        row.setManaged(isVisible);
    }

    @FXML private void handleBack() throws Exception { MainApplication.showDashboard(); }

}
