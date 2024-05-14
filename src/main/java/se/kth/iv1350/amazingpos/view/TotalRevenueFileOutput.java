package se.kth.iv1350.amazingpos.view;

import se.kth.iv1350.amazingpos.model.Filelogger;

class TotalRevenueFileOutput implements TotalRevenueObserver {

    @Override
    public void printRevenue (double totalCost) {
        Filelogger logger = new Filelogger("revenue.txt");
        logger.log("Total revenue: " + totalCost);
    }    

}
