package com.example.assignment2java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    @FXML
    private Button charactersButton; // characters button
    @FXML
    private Button spellsButton; // spells button
    @FXML
    private Label startTitle; // title for the initial scene

    // method for handling the character button being clicked
    @FXML
    private void handleCharactersButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("characters-view.fxml"));
            Parent scene2Root = loader.load();


            HelloController helloController = loader.getController();
            // Pass data to the controller

            Scene scene2 = new Scene(scene2Root, 500, 800);
            scene2.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("harrypottericon.png")));
            stage.setTitle("Harry Potter Character Book");
            stage.setScene(scene2);
        }catch (IOException a) {
            a.printStackTrace();
        }
    }

    // method for handling the spells button being clicked
    @FXML
    private void handleSpellsButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("spells-view.fxml"));
            Parent scene2Root = loader.load();


            SpellsController spellsController = loader.getController();
            // Pass data to the controller

            Scene scene2 = new Scene(scene2Root, 500, 800);
            scene2.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("harrypottericon.png")));
            stage.setTitle("Harry Potter Spell Book");
            stage.setScene(scene2);
        }catch (IOException a) {
            a.printStackTrace();
        }
    }
}
