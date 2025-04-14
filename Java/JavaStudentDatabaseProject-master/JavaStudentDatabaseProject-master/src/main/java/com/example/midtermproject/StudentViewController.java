package com.example.midtermproject;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class StudentViewController {

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, Integer> studentNumCol;

    @FXML
    private TableColumn<Student, String> firstNameCol;

    @FXML
    private TableColumn<Student, String> lastNameCol;

    @FXML
    private TableColumn<Student, String> telephoneCol;

    @FXML
    private TableColumn<Student, String> addressCol;

    @FXML
    private TableColumn<Student, String> provinceCol;

    @FXML
    private TableColumn<Student, Integer> avgGradeCol;

    @FXML
    private TableColumn<Student, String> majorCol;

    @FXML
    private CheckBox ontarioCheckBox;

    @FXML
    private Label numOfStudentsLabel;

    @FXML
    private CheckBox honourRollCheckBox;

    @FXML
    private ComboBox<String> areaCodeComboBox;

    private ObservableList<Student> studentsList;

    @FXML
    void initialize() {
        // Initialize the ComboBox
        areaCodeComboBox.getItems().add("All");

        // Initialize TableView columns
        studentNumCol.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        provinceCol.setCellValueFactory(new PropertyValueFactory<>("province"));
        avgGradeCol.setCellValueFactory(new PropertyValueFactory<>("avgGrade"));
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));

        // Initialize studentsList and load data into TableView
        studentsList = FXCollections.observableArrayList(ConnectionClass.getStudents());
        tableView.setItems(studentsList);
        updateStudentCountLabel();
        populateAreaCodeComboBox();
    }

    private void populateAreaCodeComboBox() {
        Set<String> areaCodes = new TreeSet<>(); // TreeSet for sorting

        // Extract and add area codes from telephone numbers
        for (Student student : studentsList) {
            String telephone = student.getTelephone();
            if (telephone != null && telephone.length() >= 3) {
                String areaCode = telephone.substring(0, 3);
                areaCodes.add(areaCode);
                System.out.println(areaCode);
            }
        }

        // Add sorted area codes to the ComboBox
        areaCodeComboBox.getItems().addAll(areaCodes);
    }

    @FXML
    void applyFilter() {
        // Get selected filters
        boolean ontarioOnly = ontarioCheckBox.isSelected();
        boolean honourRollOnly = honourRollCheckBox.isSelected();
        String selectedAreaCode = areaCodeComboBox.getSelectionModel().getSelectedItem();

        // Start with the full list
        List<Student> filteredList = studentsList;

        // Apply Ontario filter if selected
        if (ontarioOnly) {
            filteredList = filteredList.stream()
                    .filter(student -> student.getProvince().equalsIgnoreCase("ON"))
                    .collect(Collectors.toList());
        }

        // Apply Honour Roll filter if selected
        if (honourRollOnly) {
            filteredList = filteredList.stream()
                    .filter(student -> student.getAvgGrade() >= 80)
                    .collect(Collectors.toList());
        }

        // Apply Area Code filter if selected
        if (selectedAreaCode != null && !selectedAreaCode.equals("All")) {
            filteredList = filteredList.stream()
                    .filter(student -> student.getTelephone().startsWith(selectedAreaCode))
                    .collect(Collectors.toList());
        }

        // Update TableView with filtered list
        tableView.setItems(FXCollections.observableArrayList(filteredList));

        // Update the number of students label
        updateStudentCountLabel();
    }

    // ALREADY DONE THIS AT QUESTION 2A
    private void updateStudentCountLabel() {
        numOfStudentsLabel.setText("Number of Students: " + tableView.getItems().size());
    }
}
