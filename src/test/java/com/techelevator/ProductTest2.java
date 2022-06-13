package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest2 extends TestCase {

    public void testGetPrice() {
    } @Test

    public void Return_Get_Price() {

        //Arrange
        Product price = new Product("D4", "Triplemint", 0.75, "Gum", 5);
        BigDecimal vendPrice = price.getPrice();
        //assert
        Assert.assertEquals(0.75, vendPrice);
    }
}