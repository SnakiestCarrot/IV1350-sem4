package se.kth.iv1350.amazingpos.integration;

/**
 * Interface for discount classes.
 */
public interface Discounter {

    /**
     * Method used by discount classes to discount sale.
     * @param totalCost the cost of sale to be reduced.
     * @return returns the new price.
     */
    double discountSale(double totalCost);

}
