package com.techelevator;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest extends TestCase {

    @Test
    public void testDecreaseQuantity() {

        //Arrange
        Product actual = new Product("D4", "Triplemint", 0.75, "Gum", 5);
        actual.decreaseQuantity();
        int result = actual.getQuantity();
        //Assert
        Assert.assertEquals(4, result);

    }
}
