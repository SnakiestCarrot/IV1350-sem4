package se.kth.iv1350.amazingpos.view;

import java.util.List;

import se.kth.iv1350.amazingpos.controller.Controller;
import se.kth.iv1350.amazingpos.controller.InvalidArticleIdentifierException;
import se.kth.iv1350.amazingpos.controller.OperationFailedException;
import se.kth.iv1350.amazingpos.model.FinalSaleArticleDTO;
import se.kth.iv1350.amazingpos.model.FinalSaleDTO;
import se.kth.iv1350.amazingpos.model.SaleStatusDTO;
import se.kth.iv1350.amazingpos.model.TotalRevenueObserver;

/**
 * This view is a fake representation of the real user interface view, to be able to 
 * complete the seminar task.
 */
public class View {
    
    private Controller contr;
    
    /**
     * View constructor to make the interactions between the user interface and
     * model possible.
     * Adds revenue observers.
     * @param contr Takes a Controller instance as argument to connect it with
     * the rest of the application
     */
    public View (Controller contr) {
        this.contr = contr;
        addRevenueObserver(new TotalRevenueFileOutput());
        addRevenueObserver(new TotalRevenueView());
    }
    
    /**
     * Fakes a execution of the view running and issuing inputs and getting outputs.
     * Prints out receipt at the end.
     */
    public void runFakeView() {
        requestNewSale();       
        enterArticleIdentifier(100, 1);
        enterArticleIdentifier(1, 1);
        enterArticleIdentifier(101, 5);
        enterArticleIdentifier(101, 1);
        enterArticleIdentifier(102, 2);
        endSaleRequest();
        discountRequest();
        registerCustomerPayment(100.0);
        printReceiptRequest();
        printChangeToCustomer(contr.getFinalSaleDTO().getChange());
    }

    /**
     * Method for adding revenue obserer to controller.
     * @param revenueObserver observer to be added.
     */
    public void addRevenueObserver (TotalRevenueObserver revenueObserver) {
        this.contr.addRevenueObserver(revenueObserver);
    }

    private void requestNewSale() {
        contr.requestNewSale();
    }
    /**
     * Changed after feedback for seminar 5. 
     *
     * Prints the details of an article and the current running total after an identifier is entered.
     * This method is designed to provide a quick summary of article information and total sales cost on the console. 
     * @param articleDTO articleDTO contains details like name, price, vat rate and identifier.
     * @param totalSaleCost totalSaleCost represents the current state of the sum of all registered articles so far.
     */
    private void printAfterIdentifierEntered (SaleStatusDTO saleStatus, double quantity) {
        
        int articleID = saleStatus.getIdentifier();
        String articleName = saleStatus.getArticleName();
        double articleCost = saleStatus.getArticleCost();
        double articleVAT = saleStatus.getArticleVatRate();
        String articleDescription = saleStatus.getArticleDescription();

        double saleCost = saleStatus.getCurrentTotalSaleCost();
        double saleVAT = saleStatus.getCurrentTotalVAT();
        
        System.out.println("");
        System.out.printf("Add %.0f items with item id %d\n", quantity, articleID);
        System.out.println("Item ID: " + articleID);
        System.out.println("Item name: " + articleName);
        System.out.printf("Item cost: %.2f SEK\n", articleCost);
        System.out.printf("VAT: %.2f", + (articleVAT*100));
        System.out.print("%\n");
        System.out.println("Item description: " + articleDescription);
        System.out.println("");
        System.out.printf("Total cost (incl VAT): %.2f\n", saleCost);
        System.out.printf("Total VAT: %.2f\n", saleVAT);
    }

    private void enterArticleIdentifier (int identifier, double quantity) {
        try {
            SaleStatusDTO saleStatus = contr.enterArticle(identifier, quantity);
            printAfterIdentifierEntered(saleStatus, quantity);
        }
        catch (InvalidArticleIdentifierException exception) {
            System.out.println("Error: Identifier " + exception.getInvalidIdentifier() + " not valid. Try again.");
        }

        catch (OperationFailedException exception) {
            System.out.println("Operation failed.");
            exception.getCause();
        }

        
    }

    private void endSaleRequest () {
        System.out.println("\nEnd Sale: ");
        System.out.printf("Total cost (incl VAT): %.2f\n", contr.getCurrentTotalSaleCost());
    }

    private void registerCustomerPayment (double payment) {
        System.out.printf("\nCustomer pays %.2f SEK:\n", payment);

        contr.registerPayment(payment);
        System.out.println("Sent sale info to external accounting system.");
        System.out.println();
        printArticleListSentToInventory(this.contr.getFinalSaleDTO().getArticleList());
    }

    private void printChangeToCustomer (double change) {
        System.out.printf("Change to give to the customer: %.2f", change);
    }

    private void printReceiptRequest() {
        this.contr.printReceipt();
    }


    private void printArticleListSentToInventory (List<FinalSaleArticleDTO> articleList) {
        for (int i = 0; i < articleList.size(); i++) {
            int identifier = articleList.get(i).getIdentifier();
            double quantity = articleList.get(i).getQuantity();
            printArticleSentToInventory(identifier, quantity);
        }
        
    }
    private void printArticleSentToInventory (int identifier, double quantity) {
        System.out.printf(
                "Told external inventory system to decrease inventory quantity of item \n%d by %.0f\n" 
                    ,identifier, quantity);
    }

    private void discountRequest() {
        System.out.println("\nDiscount request!");
        FinalSaleDTO saleDTO = contr.requestDiscount();
        System.out.printf("New cost after discount is: %.2f\n" ,saleDTO.getTotalCost());
    }
}
