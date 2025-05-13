package com.example.pricecomparator.Model;

import java.time.LocalDate;

public class Discount {
    private String product_id;
    private String product_name;
    private String brand;
    private double package_quantity;
    private String package_unit;
    private String product_category;
    private LocalDate from_date;
    private LocalDate to_date;
    private double percentage_of_discount;
    private String store;

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

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public LocalDate getFrom_date() {
        return from_date;
    }

    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    public LocalDate getTo_date() {
        return to_date;
    }

    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
    }

    public double getPercentage_of_discount() {
        return percentage_of_discount;
    }

    public void setPercentage_of_discount(double percentage_of_discount) {
        this.percentage_of_discount = percentage_of_discount;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

}
