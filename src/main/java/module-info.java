module com.irina.mindhaven.mindhavendesktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;


    opens com.irina.mindhaven.mindhavendesktop to javafx.fxml;
    exports com.irina.mindhaven.mindhavendesktop;
}