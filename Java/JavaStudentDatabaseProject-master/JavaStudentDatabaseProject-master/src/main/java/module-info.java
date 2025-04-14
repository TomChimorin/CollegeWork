module com.example.midtermproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.midtermproject to javafx.fxml;
    exports com.example.midtermproject;
}