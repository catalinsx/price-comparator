package com.example.pricecomparator.Model;

import jakarta.persistence.*;

@Entity
@Table(name="Alerts")
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String userId;
    @Column(nullable = false)
    private String productId;
    @Column(nullable = false)
    private double targetPrice;

    public PriceAlert() {}
    public PriceAlert(String userId, String productId, double targetPrice){
        this.userId = userId;
        this.productId = productId;
        this.targetPrice = targetPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(double targetPrice) {
        this.targetPrice = targetPrice;
    }
}
