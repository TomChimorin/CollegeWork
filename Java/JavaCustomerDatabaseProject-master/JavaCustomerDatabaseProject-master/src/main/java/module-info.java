module com.example.comp1011finalexamsummer2024 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;


    opens com.example.comp1011finalexamsummer2024 to javafx.fxml;
    exports com.example.comp1011finalexamsummer2024;
}