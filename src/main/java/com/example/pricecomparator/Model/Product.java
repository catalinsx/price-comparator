package com.example.pricecomparator.Model;

import java.time.LocalDate;

public class Product {
    private String product_id;
    private String product_name;
    private String product_category;
    private String brand;
    private double package_quantity;
    private String package_unit;
    private double price;
    private String currency;
    private String store;
    private LocalDate date;

    public Product() {}

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPackage_quantity() {
        return package_quantity;
    }

    public void setPackage_quantity(double package_quantity) {
        this.package_quantity = package_quantity;
    }

    public String getPackage_unit() {
        return package_unit;
    }

    public void setPackage_unit(String package_unit) {
        this.package_unit = package_unit;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
