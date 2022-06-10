package com.techelevator;

import java.math.BigDecimal;

public class Product {

    private final String productName;
    private final String productType;
    private final String slot;
    private int quantity;
    private final BigDecimal price;

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
    //price to string, "5.3" indexOf(".") substring
    public BigDecimal getPrice() {

        return price.setScale(2);
    }

    public void decreaseQuantity() {
        quantity--;
    }

    }
