package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InventoryTest extends TestCase {

    public void testFindProductSlot() {



    }
    @Test
    public void Input_Returns_ProductName() throws FileNotFoundException {
        //Arrange
        File file = new File("vendingmachine.csv");
        Scanner scanner = new Scanner(file);
        Inventory inventory = new Inventory();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split("\\|");
            Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3], 5);

            inventory.getInventoryMap().put(data[1], product);

        }
        //Act
        Product expected = new Product("D4", "Triplemint", 0.75, "Gum", 5);
        Product result = inventory.findProductName("Triplemint");

        //Assert
        Assert.assertEquals(expected, result);

    }

    @Test
    public void Input_Returns_ProductSlot() throws FileNotFoundException {
        //Arrange
        File file = new File("vendingmachine.csv");
        Scanner scanner = new Scanner(file);
        Inventory inventory = new Inventory();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] data = line.split("\\|");
            Product product = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3], 5);

            inventory.getInventoryMap().put(data[1], product);

        }
        //Act
        Product expected = new Product("D4", "Triplemint", 0.75, "Gum", 5);
        Product result = inventory.findProductSlot("D4");

        //Assert
        Assert.assertEquals(expected, result);

    }
}