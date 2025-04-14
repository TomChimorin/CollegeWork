// Name: Yu Zhao
// Student Number: 200559971

package com.example.comp1011finalexamsummer2024;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 800);

        stage.setTitle("Customer Table View");

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
