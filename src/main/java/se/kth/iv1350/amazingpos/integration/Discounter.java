package se.kth.iv1350.amazingpos.integration;

import se.kth.iv1350.amazingpos.model.FinalSaleDTO;

public interface Discounter {

    double discountSale(double totalCost);

}
