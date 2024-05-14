package se.kth.iv1350.amazingpos.view;

class TotalRevenueView implements TotalRevenueObserver{

    @Override
    public void printRevenue (double totalCost) {
        System.out.println("Updated total revenue: " + totalCost);
    }  

}
