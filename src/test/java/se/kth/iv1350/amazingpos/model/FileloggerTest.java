package se.kth.iv1350.amazingpos.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.amazingpos.integration.ArticleDTO;

public class FileloggerTest {
    Filelogger instanceToTest;


    @BeforeEach
    public void setUp() {
        instanceToTest = new Filelogger("test.txt");
        
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;
    }
}
