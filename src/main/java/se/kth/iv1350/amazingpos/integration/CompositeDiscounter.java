package se.kth.iv1350.amazingpos.integration;

import java.util.ArrayList;

/**
 * Composite discounter that uses different ways to discount price.
 */
public class CompositeDiscounter implements Discounter{
    private ArrayList<Discounter> discounterList = new ArrayList<Discounter>();

    @Override
    public double discountSale (double totalCost) {
        for (int i = 0; i < discounterList.size(); i++) {
            totalCost = discounterList.get(i).discountSale(totalCost);
        }
        return totalCost;
    }

    void addDiscounter (Discounter discounter) {
        discounterList.add(discounter);
    }

    /**
     * Constructor for the composite discounter. Adds our two discounters.
     */
    public CompositeDiscounter () {
        this.addDiscounter(new AbsoluteDiscounter());
        this.addDiscounter(new PercentageDiscounter());
    }

}
