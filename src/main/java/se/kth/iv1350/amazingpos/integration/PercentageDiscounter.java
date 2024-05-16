package se.kth.iv1350.amazingpos.integration;

/**
 * Percentage based discounter class.
 * With made up discount rules for seminar.
 */
public class PercentageDiscounter implements Discounter{

    @Override
    public double discountSale (double totalCost) {
        return totalCost * 0.9; 
    }

}
