package com.techelevator;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<String, Product> inventoryMap;
    public boolean isProductInStock;

    public Inventory() {
        this.inventoryMap = new HashMap<>();
    }
    // Should this be by slot or name?
    public Product findProductSlot(String slot) {
        return inventoryMap.get(slot);
    }

    public Product findProductName(String name) {
        return inventoryMap.get(name);
    }

    public int inventorySize() {
        return inventoryMap.size();
    }

    public Product searchProductInfo(String inventoryStats) {
        String[] attributes = inventoryStats.split("\\|");
        double doublePrice = Double.parseDouble(attributes[2]);

        return new Product(attributes[0], attributes[1], doublePrice, attributes[3], 5);
    }

    public Map<String, Product> getInventoryMap() {
        return inventoryMap;
    }

    public boolean isProductInStock(String slotKey) {
        Product selectedProduct = inventoryMap.get(slotKey);
        return selectedProduct.getQuantity() > 0;
    }
}
