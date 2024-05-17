package se.kth.iv1350.amazingpos.view;

class TotalRevenueView implements TotalRevenueObserver{
    private double totalRevenue;

    @Override
    public void printRevenue (double totalCost) {
        totalRevenue = totalCost;
        System.out.printf("\nUpdated total revenue: %.2f\n" ,totalRevenue);
    }  

}
