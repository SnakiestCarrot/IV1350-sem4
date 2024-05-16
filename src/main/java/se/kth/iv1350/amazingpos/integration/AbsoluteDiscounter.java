package se.kth.iv1350.amazingpos.integration;

/**
 * Absolute discounter applies a flat discount to the total cost of sale.
 * Made up rules for seminar task.
 */
public class AbsoluteDiscounter implements Discounter {

    @Override
    public double discountSale (double totalCost) {
        if (totalCost <= 10) {
            return totalCost - 5;
        }
        else {
            return totalCost;
        }
    }
}
