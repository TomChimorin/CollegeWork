package com.example.comp1011finalexamsummer2024;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class TableViewController {
    @FXML
    private Label saleLabel;

    @FXML
    private Label msrpLabel;

    @FXML
    private Label savingsLabel;

    @FXML
    private Label rowsInTableLabel;

    @FXML
    private TableView<Customer> tableView;

    @FXML
    private TableColumn<Customer, Integer> idColumn;

    @FXML
    private TableColumn<Customer, String> firstNameColumn;

    @FXML
    private TableColumn<Customer, String> lastNameColumn;

    @FXML
    private TableColumn<Customer, String> phoneColumn;

    @FXML
    private TableColumn<Customer, String> totalPurchaseColumn;

    @FXML
    private ListView<Product> purchaseListView;

    @FXML
    private void top10Customers() {
        System.out.println("called method top10Customers()");
    }

    @FXML
    private void customersSavedOver5() {
        System.out.println("called method customersSavedOver5()");
    }

    @FXML
    public void loadAllCustomers() {
        try {
            JsonReader jsonReader = new JsonReader();
            List<Customer> customerList = jsonReader.readCustomers();
            if (customerList != null) {
                customers.clear();
                if (!customerList.isEmpty()) {
                    customers.addAll(customerList);
                } else {
                    System.out.println("Customer list is empty");
                }
            } else {
                System.out.println("Failed to load customers");
            }
            tableView.setItems(customers);
            updateRowCount(); // Call updateRowCount to set the label
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        totalPurchaseColumn.setCellValueFactory(cellData -> {
            Customer customer = cellData.getValue();
            double totalPurchases = customer.getTotalPurchases();
            return new SimpleStringProperty(String.format("$%.2f", totalPurchases));
        });

        tableView.setItems(customers);

        // Add listener to TableView selection model
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updatePurchaseListView(newValue);
            }
        });

        // Set custom cell factory for the ListView
        purchaseListView.setCellFactory(listView -> new ListCell<Product>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setText(String.format("Product - $%.2f", product.getSalePrice()));
                }
            }
        });
    }

    private void updateRowCount() {
        int rowCount = tableView.getItems().size();
        rowsInTableLabel.setText("Rows returned: " + rowCount);
    }


    private void updatePurchaseListView(Customer selectedCustomer) {
        ObservableList<Product> products = FXCollections.observableArrayList(selectedCustomer.getPurchasedProducts());
        purchaseListView.setItems(products);

        double totalRegularPrice = products.stream()
                .mapToDouble(Product::getRegularPrice)
                .sum();
        double totalSalePrice = products.stream()
                .mapToDouble(Product::getSalePrice)
                .sum();
        double totalSavings = totalRegularPrice - totalSalePrice;

        msrpLabel.setText(String.format("MSRP Total: $%.2f", totalRegularPrice));
        saleLabel.setText(String.format("Sale Total: $%.2f", totalSalePrice));
        savingsLabel.setText(String.format("Total Savings: $%.2f", totalSavings));
    }

    @FXML
    private void filterCustomersSavedOver5() {
        ObservableList<Customer> filteredCustomers = customers.stream()
                .filter(Customer::savedFiveOrMore)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        tableView.setItems(filteredCustomers);
        updateRowCount(); // Update row count after filtering
    }




}
