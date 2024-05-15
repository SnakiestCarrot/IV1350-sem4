package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.FinalSaleDTO;

public class AbsoluteDiscounter implements Discounter {

    @Override
    public double discountSale (double totalCost) {
        return totalCost - 50;
    }
}
