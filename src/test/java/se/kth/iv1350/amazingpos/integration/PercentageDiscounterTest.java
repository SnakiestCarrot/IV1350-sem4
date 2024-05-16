package se.kth.iv1350.amazingpos.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PercentageDiscounterTest {
    private PercentageDiscounter instanceToTest;

    @BeforeEach
    public void setUp() {
        instanceToTest = new PercentageDiscounter();
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }

    @Test
    public void testApplyPercentageDiscount() {
        assertTrue(90 == instanceToTest.discountSale(100));
    }
}
