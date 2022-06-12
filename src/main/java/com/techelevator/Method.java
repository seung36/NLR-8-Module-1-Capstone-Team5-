package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Scanner;

public class Method {
    private Inventory inventory;
    private String amount = "0.00";

    public Inventory createInventory(String fileName, Inventory inventory) {
        File file = new File(fileName);
        try {
            Scanner scanner = new Scanner(file);
            inventory = ((inventory == null) ? new Inventory() : inventory);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("\\|");//{"A1", "Potato Crisps", "3.05", "Chip"}
                Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3], 5);

                inventory.getInventoryMap().put(data[1], product);

            }
            scanner.close();
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        }

        return inventory;
    }

    public void displayInventory(Inventory inventory) {
        int index = 0;
        String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);
        for (Map.Entry<String, Product> map : inventory.getInventoryMap().entrySet()) {
            Product pr = inventory.getInventoryMap().get(keys[index]);
            System.out.println(pr.getSlot() + " " + pr.getProductName() + " $" + pr.getPrice() + " Qty: " + (pr.getQuantity() == 0 ? "SOLD OUT" : pr.getQuantity()));
            index++;
        }
    }

    public void printCurrentMoney(String amount) {
        System.out.println(System.lineSeparator() + "Current Money Provided" + " $" + BigDecimal.valueOf(Double.parseDouble(amount)).setScale(2));
    }

    public String selectProduct(String slot, Inventory inventory, String amount) {
        String[] keys = inventory.getInventoryMap().keySet().toArray(new String[0]);

            String item = slot;
            boolean doesProductExist = false;
            for (String key : keys) {

                Product product = inventory.getInventoryMap().get(key);
                String productSlot = product.getSlot();
                if (productSlot.equalsIgnoreCase(item) && product.getQuantity() > 0) {
                    doesProductExist = true;
                    String productType = product.getProductType();
                    BigDecimal price = product.getPrice();
                    BigDecimal bdAmt = BigDecimal.valueOf(Double.parseDouble(amount));
                    if (bdAmt.compareTo(price) >= 0) {
                        amount = bdAmt.subtract(price).toString();
                        product.decreaseQuantity();

                        switch (productType) {

                            case "Chip":
                                return "Crunch Crunch, Crunch!";

                            case "Gum":
                                return "Chew Chew, Pop!";

                            case "Drink":
                                return "Cheers Glug, Glug!";

                            case "Candy":
                                return "Munch Munch, Mmm-Good!";
                              
                            default:
                        }

                    }
                }

            }
            if (!doesProductExist) {
                return "Invalid Code! or SOLD OUT";
            }
//								if(!inventory.isProductInStock) {
//									System.out.println("SOLD OUT!");
//								}

        return "";
    }
}
