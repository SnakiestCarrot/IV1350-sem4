package se.kth.iv1350.amazingpos.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbsoluteDiscounterTest {
    private AbsoluteDiscounter instanceToTest;

    @BeforeEach
    public void setUp() {
        instanceToTest = new AbsoluteDiscounter();
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }

    @Test
    public void testCorrectSubtraction() {
        assertTrue(10 == instanceToTest.discountSale(15), "The discount was incorrectly applied.");
    }

    @Test
    public void testLessThanMinimumTotalCost() {
        assertTrue(7 == instanceToTest.discountSale(7), "A discount was applied on a total less than 10.");
    }
}
