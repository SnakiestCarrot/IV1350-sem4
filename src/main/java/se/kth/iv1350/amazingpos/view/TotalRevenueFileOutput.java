package se.kth.iv1350.amazingpos.view;

import se.kth.iv1350.amazingpos.model.Filelogger;
import se.kth.iv1350.amazingpos.model.TotalRevenueObserver;

/**
 * Writes total revenue to a file.
 */
class TotalRevenueFileOutput implements TotalRevenueObserver {
    private double totalRevenue;

    @Override
    public void printRevenue (double totalCost) {
        totalRevenue += totalCost;
        Filelogger logger = new Filelogger("revenue.txt");
        logger.logMessage("Total revenue: " + totalRevenue);
    }    

}
