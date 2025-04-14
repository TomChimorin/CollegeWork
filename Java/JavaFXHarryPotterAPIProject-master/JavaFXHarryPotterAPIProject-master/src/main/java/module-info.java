module com.example.assignment2java {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.desktop;


    opens com.example.assignment2java to javafx.fxml;
    exports com.example.assignment2java;
}