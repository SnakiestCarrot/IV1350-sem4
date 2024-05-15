package se.kth.iv1350.amazingpos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.amazingpos.integration.ArticleDTO;
import se.kth.iv1350.amazingpos.view.TotalRevenueObserver;

/**
 * Sale is responsible for storing information related to the transaction.
 * It holds details about the cost of the transaction, the time at which it was initiated, 
 * which items are involved, the total sale VAT, the payment from the customer and the change to be returned.
 */
public class Sale {
    private double totalCost;
    private LocalDateTime saleTime;
    private double totalSaleVAT;
    private double payment;
    private double change;
    private ArrayList<Article> articleList = new ArrayList<Article>();
    private ArrayList<TotalRevenueObserver> revenueObserversList =  new ArrayList<TotalRevenueObserver>();
    
    /**
     * Makes a sale object.
     */
    public Sale() {
        setSaleTime();
    }
    
    private void setSaleTime() {
        this.saleTime = LocalDateTime.now();
    }
    
    public double getTotalCost () {
        return this.totalCost;
    }
    
    public LocalDateTime getSaleTime () {
        return this.saleTime;
    }
    
    public double getTotalSaleVAT () {
        return this.totalSaleVAT;
    }

    public double getPayment () {
        return this.payment;
    }

    public double getChange () {
        return this.change;
    }

    public ArrayList<Article> getArticleList () {
        return this.articleList;
    }

    private boolean isArticleInSale (ArticleDTO artDTO) {
        for (int i = 0; i < articleList.size(); i++) {
            if (articleList.get(i).getIdentifier() == artDTO.getIdentifier()) {
                return true;
            }
        }
        return false;
    }

    private void addQuantityToArticleInList (ArticleDTO articleToAdd, double quantity) {
        for (int i = 0; i < articleList.size(); i++) {
            if (articleList.get(i).getIdentifier() == articleToAdd.getIdentifier()) {
                articleList.get(i).addQuantity(quantity);
            }
        }
    }

    private void createArticleInList (ArticleDTO artDTO, double quantity) {
        articleList.add(new Article(artDTO, quantity));
    }

    private void updateSaleTotalCost () {
        double newTotalCost = 0;
        for (int i = 0; i < articleList.size(); i++) {
            newTotalCost += calculateArticleCost(articleList.get(i));
        }

        this.totalCost = newTotalCost;
    }

    private double calculateArticleCost (Article article) {
        return article.getPrice() * article.getQuantity();
    }

    private double calculateArticleVAT (Article article) {
        return calculateArticleCost(article) * article.getVatRate();
    }

    private void updateTotalVATForSale () {
        double newTotalSaleVAT = 0;
        for (int i = 0; i < articleList.size(); i++) {
            newTotalSaleVAT += calculateArticleVAT(articleList.get(i));
        }

        this.totalSaleVAT = newTotalSaleVAT;
    }

    private Article getArticleInList (ArticleDTO artDTO) {
        for (int i = 0; i < articleList.size(); i++) {
            if (artDTO.getIdentifier() == articleList.get(i).getIdentifier()) {
                return articleList.get(i);
            }
        }
        return null;
    }

    /**
     * enterArticleToSale takes an ArticleDTO and a quantity to update cost and VAT for the sale.
     * Notifies all observers with updated price.
     * @param artDTO artDTO is a DTO containing details about the article, such as name, price, vat rate, etc.
     * @param quantity quantity represents how many of each articles are to be added to the sale.
     * @return
     */
    public SaleStatusDTO enterArticleToSale (ArticleDTO artDTO, double quantity) {
        if (isArticleInSale(artDTO)) {
            addQuantityToArticleInList(artDTO, quantity);
        }
        else {
            createArticleInList(artDTO, quantity);
        }
        updateSaleTotalCost();
        updateTotalVATForSale();
        notifyRevenueObserver(getTotalCost());
        return new SaleStatusDTO (getArticleInList(artDTO), this.totalCost, this.totalSaleVAT);
    }

    /**
     * Method for register payment to sale. Sets payment and change attributes.
     * 
     * @param payment
     */
    public void registerFinalPayment (double payment) {
        this.payment = payment;
        this.change = payment - totalCost;
    }


    /**
     * Adds a revenue observers to sale class from list of observers.
     * @param revenueObserversList
     */
    public void addObservers (ArrayList<TotalRevenueObserver> contrObserversList) {
        for (int i = 0; i < contrObserversList.size(); i++) {
            this.revenueObserversList.add(contrObserversList.get(i));
        }
    }
     
    private void notifyRevenueObserver (double totalCost) {
        for (TotalRevenueObserver revenueObserver : revenueObserversList) {
            revenueObserver.printRevenue(totalCost);
        }
    }
}

    
