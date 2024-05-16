package se.kth.iv1350.amazingpos.integration;

public class PercentageDiscounter implements Discounter{

    @Override
    public double discountSale (double totalCost) {
        return totalCost * 0.9; 
    }

}
