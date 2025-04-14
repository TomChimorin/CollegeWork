package com.example.assignment2java;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SpellsController {

    @FXML
    private Button backButton; // back button from spells scene to initial scene
    @FXML
    private AnchorPane root; // root container for this scene
    @FXML
    private Button previousButton; // previous button for spells
    @FXML
    private Label titleLabel2; // title for this scene
    @FXML
    private Button nextButton; // next button for spells
    @FXML
    private Label spellNameLabel; // spell names
    @FXML
    private Label spellDescriptionLabel; // spell descriptions
    @FXML
    private TextArea spellDetailsTextArea; // spell details

    private JsonArray spellsArray; // JSON for spells
    private int currentIndex = 0; // index for each spell

    @FXML
    public void initialize() { // method to call the getSpells method plus some exceptions
        // Fetch spells when the application starts
        String result = getSpells();
        if (result.startsWith("Error") || result.startsWith("Exception")) {
            spellNameLabel.setText(result);
        } else {
            parseSpells(result);
            showSpell(currentIndex);
        }
    }

    // method for fetching the spells from the API
    private String getSpells() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://harry-potter3.p.rapidapi.com/api/spells"))
                    .header("x-rapidapi-key", "0864445c1fmshb526d3f0e597f93p1f4e91jsndef178f19d55")
                    .header("x-rapidapi-host", "harry-potter3.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                return "Error: " + response.statusCode();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Exception occurred: " + e.getMessage();
        }
    }

    // parsing the spells from the JSON
    private void parseSpells(String jsonResponse) {
        try {
            JsonArray fullArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
            spellsArray = JsonParser.parseString(jsonResponse).getAsJsonArray();
            // Add any specific filtering or processing logic for spells here
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // displaying each spells' information to the scene
    private void showSpell(int index) {
        if (spellsArray != null && index >= 0 && index < spellsArray.size()) {
            JsonObject spell = spellsArray.get(index).getAsJsonObject();
            spellNameLabel.setText("Name: " + getStringFromJson(spell, "name"));
            spellDescriptionLabel.setText("Description: " + getStringFromJson(spell, "description"));
            spellDetailsTextArea.setText("Details: " + getStringFromJson(spell, "details"));

            // Update button states
            previousButton.setDisable(index == 0);
            nextButton.setDisable(index >= spellsArray.size() - 1);
        }
    }

    // method for getting string from JSON
    private String getStringFromJson(JsonObject jsonObject, String key) {
        JsonElement element = jsonObject.get(key);
        if (element != null && !element.isJsonNull()) {
            return element.getAsString();
        }
        return "N/A"; // or any default value
    }

    // method for handling previous button being clicked
    @FXML
    private void onPreviousButtonClick(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            showSpell(currentIndex);
        }
    }

    // method for handling next button being clicked
    @FXML
    private void onNextButtonClick(ActionEvent event) {
        if (currentIndex < spellsArray.size() - 1) {
            currentIndex++;
            showSpell(currentIndex);
        }
    }

    // method for handling the spells button being clicked from the initial scene
    @FXML
    private void handleSpellsButtonClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("spells-view.fxml"));
            Parent scene2Root = loader.load();

            SpellsController spellsController = loader.getController();
            // Pass data to the controller if needed

            Scene scene2 = new Scene(scene2Root, 500, 800); // Adjust size if needed
            // Add the CSS file to the scene
            scene2.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("harrypottericon.png")));

            stage.setScene(scene2);
        } catch (IOException a) {
            a.printStackTrace();
        }
    }

    // method for handling the back button being clicked from the spells scene to the initial scene
    @FXML
    private void handleBackButtonClick() {
        try {
            // Load the start view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
            Parent startView = loader.load();
            Scene newScene = new Scene(startView, 500, 800);
            // Apply CSS if needed
            newScene.getStylesheets().add(getClass().getResource("/com/example/assignment2java/styles.css").toExternalForm());

            // Get the current stage
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.getIcons().add(new Image(getClass().getResourceAsStream("harrypottericon.png")));
            stage.setTitle("Harry Potter Magic Book");
            // Set the scene to the start view
            stage.setScene(newScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
