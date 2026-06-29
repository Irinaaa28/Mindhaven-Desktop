package com.irina.mindhaven.mindhavendesktop.rule;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.api.ApiClient;
import com.irina.mindhaven.mindhavendesktop.session.SessionContext;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;

public class MyRulesController {

    @FXML private TableView<RuleDTO> rulesTable;
    @FXML private TableColumn<RuleDTO, Long> idColumn;
    @FXML private TableColumn<RuleDTO, String> typeColumn;
    @FXML private TableColumn<RuleDTO, String> valueColumn;

    @FXML private Label detailId;
    @FXML private Label detailType;
    @FXML private Label detailCategory;
    @FXML private Label detailValue;
    @FXML private Label detailMaxMinutes;
    @FXML private Label detailTimeWindow;
    @FXML private Label detailActive;

    @FXML private HBox rowValue;
    @FXML private HBox rowCategory;
    @FXML private HBox rowMaxMinutes;
    @FXML private HBox rowTimeWindow;

    private final ApiClient apiClient = MainApplication.apiClient;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        loadUserRules();

        rulesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null)
                populateDetails(newSelection);
        });
    }

    public void loadUserRules() {
        new Thread(() -> {
            try {
                String currentUuid = SessionContext.getUserUuid();
                if (currentUuid != null) {
                    List<RuleDTO> rules = apiClient.getRulesByUser(currentUuid);
                    Platform.runLater(() -> {
                        rulesTable.setItems(FXCollections.observableArrayList(rules));
                        rulesTable.refresh();
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void handleViewDetails() {
        RuleDTO selectedRule = rulesTable.getSelectionModel().getSelectedItem();
        if (selectedRule == null) {
            new Alert(Alert.AlertType.WARNING, "Select a rule", ButtonType.OK).showAndWait();
            return;
        }
        populateDetails(selectedRule);
    }

    private void populateDetails(RuleDTO rule) {
        detailId.setText(String.valueOf(rule.getId()));
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

    }

    private void toggleRow(HBox row, boolean isVisible) {
        row.setVisible(isVisible);
        row.setManaged(isVisible);
    }

    @FXML private void handleBack() throws Exception { MainApplication.showDashboard(); }
}
