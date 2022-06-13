package com.techelevator;

import junit.framework.TestCase;
import org.junit.Test;

public class FinancialTest extends TestCase {
    @Test
    public void testReturnChange() {
        String expected = "Change returned: 1 quarters, 1 dimes, 1 nickels";
        assertEquals(expected, Financial.returnChange("0.40"));
        expected = "Change returned: 0 quarters, 0 dimes, 0 nickels";
        assertEquals(expected, Financial.returnChange("0"));
    }
}