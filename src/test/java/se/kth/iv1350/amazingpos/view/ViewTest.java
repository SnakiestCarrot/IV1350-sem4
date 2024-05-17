package se.kth.iv1350.amazingpos.view;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.controller.Controller;
import se.kth.iv1350.amazingpos.integration.ArticleCatalogHandler;
import se.kth.iv1350.amazingpos.integration.ExternalAccountingManager;
import se.kth.iv1350.amazingpos.integration.ReceiptPrinter;
import se.kth.iv1350.amazingpos.model.Sale;

public class ViewTest {
    private View instanceToTest;
    
    @BeforeEach
    public void setUp() {
        ReceiptPrinter printer = new ReceiptPrinter();
        ExternalAccountingManager accountingManager = ExternalAccountingManager.getExternalAccountingManager();
        ArticleCatalogHandler catalogHandler = ArticleCatalogHandler.getArticleCatalogHandler();
        
        Controller contr = new Controller(printer, accountingManager, catalogHandler);
        
        this.instanceToTest = new View(contr);
        
        
        
    }
    
    @AfterEach
    public void tearDown() {
        this.instanceToTest = null;
    }

    @Test
    public void testRunFakeView() {
        instanceToTest.runFakeView();
    }

    @Test
    public void testRevenueFileOutput() {
        instanceToTest.addRevenueObserver(new TotalRevenueView());
        instanceToTest.addRevenueObserver(new TotalRevenueFileOutput());
        instanceToTest.runFakeView();

        StringBuilder builder = new StringBuilder();
        String str;
        String outcome;

        try {
            BufferedReader buffer = new BufferedReader(new FileReader("revenue.txt"));
            while ((str = buffer.readLine()) != null) {
 
                builder.append(str.split(":")[0]);
            }
            outcome = builder.toString();
            buffer.close();

            assertTrue(outcome.equals("Total revenue"), "Not writing correct message to file.");
        }

        catch (FileNotFoundException exception){
            fail("FileNotFoundException.");
        }
        catch (IOException exception) {
            fail("IOEXception");
        }  
        
    }

}
