// Name: Yu Zhao
// Student Number: 200559971

package com.example.comp1011finalexamsummer2024;

public class Product {
    private String sku;
    private String name;
    private double salePrice;
    private double regularPrice;
    private String imageURL; // Holds the URL information for the image

    // Constructor
    public Product(String sku, String name, double salePrice, double regularPrice, String imageURL) {
        this.sku = sku;
        this.name = name;
        this.salePrice = salePrice;
        this.regularPrice = regularPrice;
        this.imageURL = imageURL;
    }

    // Getters and Setters
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    // toString() method
    @Override
    public String toString() {
        return name + "-$" + String.format("%.2f", salePrice);
    }
}