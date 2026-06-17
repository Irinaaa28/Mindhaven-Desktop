package com.irina.mindhaven.mindhavendesktop.ratelimit;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
import javafx.fxml.FXML;

import java.io.IOException;

public class RateLimitController {

    @FXML
    private void goToLogin() throws Exception {
        MainApplication.showAuthenticate();
    }
}
