package se.kth.iv1350.amazingpos.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.integration.ArticleCatalogHandler;
import se.kth.iv1350.amazingpos.integration.ArticleDTO;
import se.kth.iv1350.amazingpos.integration.ArticleDTONotFoundException;
import se.kth.iv1350.amazingpos.integration.DatabaseFailureException;
import se.kth.iv1350.amazingpos.integration.ExternalAccountingManager;
import se.kth.iv1350.amazingpos.integration.ReceiptPrinter;
import se.kth.iv1350.amazingpos.model.Article;
import se.kth.iv1350.amazingpos.model.Sale;


public class ControllerTest {
    private Controller testController;
    private ReceiptPrinter testPrinter;
    private ExternalAccountingManager testAccMan;
    private ArticleCatalogHandler testCatHan;
    private ArticleDTO testArticleDTO;



    @BeforeEach
    public void setUp() {

        testPrinter = new ReceiptPrinter();
        testAccMan = ExternalAccountingManager.getExternalAccountingManager();
        testCatHan = ArticleCatalogHandler.getArticleCatalogHandler();

        testController = new Controller(testPrinter, testAccMan, testCatHan);
        testController.requestNewSale();
    }

    @AfterEach
    public void tearDown() {
        testPrinter = null;
        testAccMan = null;
        testCatHan = null;

        testController = null;
    }
    
    @Test
    public void testEnterArticle() {
        testArticleDTO = new ArticleDTO(101, 2.99, 0.25, "Banana", "This is a banana");

        int quantity = 1;
        
        Sale sale = new Sale();
        sale.enterArticleToSale(testArticleDTO, quantity);

        assertEquals(quantity, sale.getArticleList().get(0).getQuantity());
    }

    /* 
     * This test is to address the following feedback
     * 2. The tests for the controller are not complete, there's for example no test for alternative flow 3-4b.
     */
    @Test
    public void testRepeatedArticleEntry() {
        testArticleDTO = new ArticleDTO(101, 2.99, 0.25, "Banana", "This is a banana");

        int quantity = 1;
        
        Sale sale = new Sale();
        sale.enterArticleToSale(testArticleDTO, quantity);
        sale.enterArticleToSale(testArticleDTO, quantity);

        assertEquals(quantity * 2, sale.getArticleList().get(0).getQuantity());
    }

    @Test
    public void testAddingMultipleUniqueItemsToSale () {
        ArticleDTO testArticleDTOBanana = new ArticleDTO(101, 2.99, 0.25, "Banana", "This is a banana");
        ArticleDTO testArticleDTOOrange = new ArticleDTO(102, 1.99, 0.25, "Orange", "This is an orange");

        Article testBanana = new Article(testArticleDTOBanana, 1);
        Article testTwoBananas = new Article(testArticleDTOBanana, 2);
        Article testOrange = new Article(testArticleDTOOrange, 1);

        Sale sale = new Sale();

        sale.enterArticleToSale(testArticleDTOBanana, 1);        
        sale.enterArticleToSale(testArticleDTOOrange, 1);

        assertTrue(sale.getArticleList().get(0).equals(testBanana) && 
        sale.getArticleList().get(1).equals(testOrange), "Did not correctly add unique articles to sale.");

        sale.enterArticleToSale(testArticleDTOBanana, 1);

        assertTrue(sale.getArticleList().get(0).equals(testTwoBananas) && 
        sale.getArticleList().get(1).equals(testOrange), "Did not update article quantity when adding a previously entered item.");
    }
    
    @Test
    public void testRegisterPayment() {
        Controller controller = new Controller(new ReceiptPrinter(), 
                                               ExternalAccountingManager.getExternalAccountingManager(), 
                                               ArticleCatalogHandler.getArticleCatalogHandler());
        controller.requestNewSale();
        double payment = 50.0;
        
        controller.registerPayment(payment);
        
        assertNotNull(controller.getFinalSaleDTO());
        assertEquals(payment, controller.getFinalSaleDTO().getPayment());
        assertEquals(payment - controller.getFinalSaleDTO().getTotalCost(), controller.getFinalSaleDTO().getChange());
    }

    @Test
    public void testEnterInvalidArticle() {
        assertThrows(InvalidArticleIdentifierException.class, () -> {
            testController.enterArticle(-1, 10);
        }, "expected InvalidArticleIdentifierException, but such an exception was not thrown.");
    }

    @Test
    public void testEnterArticleOperationFailed () {
        assertThrows(OperationFailedException.class, () -> {
            testController.enterArticle(100, 1);
        }, "Expected OperationFailedException, but such an exception was not thrown.");
    }

    @Test
    public void testEnterArticleOperationFailedMsg () {
        try {
            testController.enterArticle(100, 1);
        }
        catch (OperationFailedException e) {
            assertEquals("Could not get article.", e.getMessage(), "The thrown OperationFailedException did not have the expected message.");
        }
        catch (InvalidArticleIdentifierException e) {
            fail("Expected OperationFailedException but InvalidArticleException was thrown.");
        }
    }

    @Test
    public void testEnterArticleOperationFailedCause () {
        try {
            testController.enterArticle(100, 1);
        }
        catch (OperationFailedException e) {
            assertTrue(e.getCause() instanceof DatabaseFailureException, "The thrown OperationFailedException did not have the expected message.");
        }
        catch (InvalidArticleIdentifierException e) {
            fail("Expected OperationFailedException but InvalidArticleException was thrown.");
        }
    }

    @Test
    public void testEnterArticleInvalidIdentifierMsg () {
        try {
            testController.enterArticle(-1, 1);
        }
        catch (InvalidArticleIdentifierException e) {
            assertEquals(-1, e.getInvalidIdentifier(), "The thrown InvalidArticleIdentifierException did not have the expected invalid identifer.");
        }
        catch (OperationFailedException e) {
            fail("Expected InvalidArticleIdentifierException but OperationFailedException was thrown");
        }
    }
}