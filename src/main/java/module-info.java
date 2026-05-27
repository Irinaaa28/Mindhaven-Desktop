module com.irina.mindhaven.mindhavendesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;
    requires jdk.jfr;
    requires com.fasterxml.jackson.databind;
    requires com.sun.jna.platform;
    requires com.sun.jna;


    opens com.irina.mindhaven.mindhavendesktop to javafx.fxml;
//    opens com.irina.mindhaven.mindhavendesktop.models to javafx.base;
    exports com.irina.mindhaven.mindhavendesktop;
    exports com.irina.mindhaven.mindhavendesktop.home;
    opens com.irina.mindhaven.mindhavendesktop.home to javafx.fxml;
    exports com.irina.mindhaven.mindhavendesktop.api;
    opens com.irina.mindhaven.mindhavendesktop.api to javafx.fxml;
    exports com.irina.mindhaven.mindhavendesktop.auth;
    opens com.irina.mindhaven.mindhavendesktop.auth to javafx.fxml;
    exports com.irina.mindhaven.mindhavendesktop.log;
    exports com.irina.mindhaven.mindhavendesktop.user;
    opens com.irina.mindhaven.mindhavendesktop.user to com.fasterxml.jackson.databind, javafx.base, javafx.fxml;
    opens com.irina.mindhaven.mindhavendesktop.log to com.fasterxml.jackson.databind, javafx.base, javafx.fxml;
}