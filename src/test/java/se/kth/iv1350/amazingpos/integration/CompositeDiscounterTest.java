package se.kth.iv1350.amazingpos.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompositeDiscounterTest {
    private CompositeDiscounter instanceToTest;

    @BeforeEach
    public void setUp() {
        instanceToTest = new CompositeDiscounter();
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }

    @Test
    public void testApplyAllDiscounts() {
        assertTrue(45 == instanceToTest.discountSale(55), "Discount was not applied correctly.");
    }

    @Test
    public void testApplyPercentageDiscountOnly() {
        assertTrue(9 * 0.9 == instanceToTest.discountSale(9), "Both or neither discount was applied.");
    }
}
