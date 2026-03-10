package com.irina.mindhaven.mindhavendesktop.controllers;

import com.irina.mindhaven.mindhavendesktop.MainApplication;
import javafx.fxml.FXML;

import java.awt.*;
import java.net.URI;

public class HomeController {
    @FXML
    private void goToAuthenticate() throws Exception {
        MainApplication.showAuthenticate();
    }

    @FXML
    private void goToRegister() throws Exception {
        MainApplication.showRegister();
    }

    @FXML
    private void loginGoogle() throws Exception {
        Desktop.getDesktop().browse(
                URI.create("http://localhost:8080/oauth2/authorization/google"));
    }

}
