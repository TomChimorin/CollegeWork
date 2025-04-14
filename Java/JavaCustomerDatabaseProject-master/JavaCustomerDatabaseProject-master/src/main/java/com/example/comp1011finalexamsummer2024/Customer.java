package com.example.comp1011finalexamsummer2024;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private List<Product> purchasedProducts;

    public Customer(int id, String firstName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.purchasedProducts = new ArrayList<>();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Product> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(List<Product> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public double getTotalPurchases() {
        return purchasedProducts.stream()
                .mapToDouble(Product::getSalePrice)
                .sum();
    }

    public double getTotalSavings() {
        return purchasedProducts.stream()
                .mapToDouble(p -> p.getRegularPrice() - p.getSalePrice())
                .sum();
    }

    public boolean savedFiveOrMore() {
        return purchasedProducts.stream()
                .allMatch(p -> (p.getRegularPrice() - p.getSalePrice()) >= 5);
    }
}
