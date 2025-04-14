package com.example.assignment2java;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file for the start view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));

        // Create a new Scene with the loaded FXML, and set its size to 500x800
        Scene scene = new Scene(loader.load(), 500, 800);

        // Add the CSS file to the scene to apply custom styles
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Set the application icon
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("harrypottericon.png")));

        // Set the title of the primary stage
        primaryStage.setTitle("Harry Potter Magic Book");

        // Set the scene on the primary stage and display it
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
