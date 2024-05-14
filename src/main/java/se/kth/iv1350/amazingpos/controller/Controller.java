package se.kth.iv1350.amazingpos.controller;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.amazingpos.integration.*;
import se.kth.iv1350.amazingpos.model.Filelogger;
import se.kth.iv1350.amazingpos.model.FinalSaleDTO;
import se.kth.iv1350.amazingpos.model.Sale;
import se.kth.iv1350.amazingpos.model.SaleStatusDTO;
import se.kth.iv1350.amazingpos.view.TotalRevenueObserver;

/**
 * This is the application's controller, all method calls from view go through this class.
 */
public class Controller {
    private ReceiptPrinter printer;
    private ExternalAccountingManager accountingManager;
    private ArticleCatalogHandler catalogHandler;
    private Sale sale;
    private List<TotalRevenueObserver> revenueObserversList = new ArrayList<>();

    /**
     * Initializes a new instance of the Controller class, setting up the necessary components for handling receipt printing,
     * accounting updates, and article information retrieval.
     * 
     * @param printer Takes a ReceiptPrinter instance to later be used for the printing of the receipt.
     * @param accountingManager Takes an ExternalAccountingManager instance to later update the external accounting system
     * @param catalogHandler Takes an ArticleCatalogHandler instance to fetch article information
     */
    public Controller (ReceiptPrinter printer,
            ExternalAccountingManager accountingManager,
            ArticleCatalogHandler catalogHandler) 
    {
        this.printer = printer;
        this.accountingManager = accountingManager;
        this.catalogHandler = catalogHandler;
    }
    
    
    /**
     * Creates instance of Sale class.
     */
    public void requestNewSale() {
        this.sale = new Sale();
        sale.addObservers(revenueObserversList);
    }

    public void addRevenueObserver(TotalRevenueObserver revenueObserver) {
        revenueObserversList.add(revenueObserver);
    }
    
    private ArticleDTO fetchArticleDTO (int identifier) throws ArticleDTONotFoundException, DatabaseFailureException {
            return catalogHandler.fetchArticleDTO(identifier);
    }

    /**
     * Method to enter article based on identifier and quantity to the sale object.
     * Returns a SaleStatusDTO for view to display information.
     * 
     * @param identifier Article Identifier entered from view
     * @param quantity Quantity of article
     * @return SaleStausDTO  
     * @throws InvalidArticleIdentifierException writes error to log file.
     * @throws DatabaseFailureException writes error to log file.
     */
    public SaleStatusDTO enterArticle (int identifier, double quantity) throws InvalidArticleIdentifierException, DatabaseFailureException {
        try {
            return this.sale.enterArticleToSale(fetchArticleDTO(identifier), quantity);
        }
        
        catch (ArticleDTONotFoundException exception) {
            Filelogger logger = new Filelogger();
            logger.log("ArticleDTONotFoundException: Article DTO based on identifier: " + 
                        exception.getInvalidIdentifier() + " not found in Article Catalog.");
            throw new InvalidArticleIdentifierException(exception.getInvalidIdentifier());
        }   

        catch (DatabaseFailureException exception) {
            Filelogger logger = new Filelogger();
            logger.log("Could not connect to database.");
            throw new DatabaseFailureException();
        }
        
    }

    public double getCurrentTotalSaleCost () {
        return this.sale.getTotalCost();
    }

    /**
     * Registers payment to sale. 
     * @param payment
     */
    public void registerPayment (double payment) {
        this.sale.registerFinalPayment(payment);

        accountingManager.updateAccountingSystem(this.sale.getTotalCost());


        catalogHandler.updateInventorySystem(this.sale.getArticleList());
    }

    public FinalSaleDTO getFinalSaleDTO () {
        return new FinalSaleDTO(this.sale);
    }

    /**
     * Tells printer to print receipt.
     */
    public void printReceipt ()  {
        this.printer.printReceipt(getFinalSaleDTO());
    }
}
