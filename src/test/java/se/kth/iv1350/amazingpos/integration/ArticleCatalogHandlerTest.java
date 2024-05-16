/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package se.kth.iv1350.amazingpos.integration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.controller.InvalidArticleIdentifierException;



/**
 *
 * behövs det kommentarer för testerna?
 */
public class ArticleCatalogHandlerTest {
    private ArticleCatalogHandler instanceToTest;

    @BeforeEach
    public void setUp() {
        instanceToTest = ArticleCatalogHandler.getArticleCatalogHandler();
    }
    
    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }

    @Test
    public void testFetchArticleDTOisValid() {
        ArticleDTO expectedOutput = new ArticleDTO(101, 2.99, 0.25, "Banana", "This is a banana");
        ArticleDTO actualOutput = null;
        try {
            actualOutput = instanceToTest.fetchArticleDTO(101);
        } catch (Exception e) {
            fail();
        }

        assertTrue(actualOutput.equals(expectedOutput), "The two ArticleDTO objects do not match.");
    }

    @Test
    public void testFetchArticleDTODifferentIdentifier() {
        ArticleDTO expectedOutput = new ArticleDTO(102, 1.99, 0.25, "Orange", "This is an orange");
        ArticleDTO actualOutput = null;
        try {
            actualOutput = instanceToTest.fetchArticleDTO(103);
        } catch (Exception e) {
            fail("An exception was thrown.");
        } 

        assertFalse(actualOutput.equals(expectedOutput), "The two ArticleDTO objects match.");
    }

    @Test
    public void testFetchArticleDTOisNotValidLowerLimitNegative() {
        assertThrows(ArticleDTONotFoundException.class, () -> {
            instanceToTest.fetchArticleDTO(-1);
        });
    }

    @Test
    public void testFetchArticleDTOisNotValidLowerLimitPositive() {
        assertThrows(ArticleDTONotFoundException.class, () -> {
            instanceToTest.fetchArticleDTO(95);
        });
    }

    @Test
    public void testFetchArticleDTOisNotValidLowerLimitZero() {
        assertThrows(ArticleDTONotFoundException.class, () -> {
            instanceToTest.fetchArticleDTO(0);
        });
    }

    @Test
    public void testFetchArticleDTOisNotValidUpperLimit() {
        assertThrows(ArticleDTONotFoundException.class, () -> {
            instanceToTest.fetchArticleDTO(150);
        });
    }

    @Test
    public void testFetchArticleDTODatabaseFailure() {
        assertThrows(DatabaseFailureException.class, () -> {
            instanceToTest.fetchArticleDTO(100);
        }, "Expected an exception of type DataBaseFailureException to be thrown.");
    }
}
