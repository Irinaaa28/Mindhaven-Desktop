module com.irina.mindhaven.mindhavendesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;
    requires jdk.jfr;
    requires com.fasterxml.jackson.databind;


    opens com.irina.mindhaven.mindhavendesktop to javafx.fxml;
//    opens com.irina.mindhaven.mindhavendesktop.models to javafx.base;
    exports com.irina.mindhaven.mindhavendesktop;
    exports com.irina.mindhaven.mindhavendesktop.controllers;
    opens com.irina.mindhaven.mindhavendesktop.controllers to javafx.fxml;
    exports com.irina.mindhaven.mindhavendesktop.services;
    opens com.irina.mindhaven.mindhavendesktop.services to javafx.fxml;
    opens com.irina.mindhaven.mindhavendesktop.models to com.fasterxml.jackson.databind, javafx.base;
}