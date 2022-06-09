package com.techelevator;

import java.math.BigDecimal;

public class Product {

    private String productName;
    private String productType;
    private String slot;
    private int quantity;
    private BigDecimal price;

    // insert method
    public Product(String slot, String name, double price, String type, int quantity) {
        this.slot = slot;
        this.productName = name;
        this.price = BigDecimal.valueOf(price);
        this.productType = type;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductType() {
        return productType;
    }

    public String getSlot() {
        return slot;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity() {
        quantity--;
    }

    public String soldOut() {
        if (quantity == 0) {
            return "SOLD OUT";
        } else {
            return Integer.toString(quantity);
        }
    }
}
