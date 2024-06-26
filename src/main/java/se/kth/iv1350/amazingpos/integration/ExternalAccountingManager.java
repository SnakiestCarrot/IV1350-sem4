/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.amazingpos.integration;

/**
 * ExternalAccountingManager will represent the system that handles the payment 
 * and change of the transaction. 
 */
public class ExternalAccountingManager {
    private static final ExternalAccountingManager EXTERNAL_ACCOUNTING_MANAGER = new ExternalAccountingManager();

    private double funds = 9999;

    private ExternalAccountingManager () {

    }

    /**
     * Method for updating accounting system. Adds sale to attribute "funds".
     * Made up representation of system made for seminar task.
     * 
     * @param saleTotalCost
     */
    public void updateAccountingSystem (double saleTotalCost) {
        this.funds = this.funds + saleTotalCost;
    }

    public double getFunds() {
        return this.funds;
    }
    
    public static ExternalAccountingManager getExternalAccountingManager () {
        return EXTERNAL_ACCOUNTING_MANAGER;
    }
}
