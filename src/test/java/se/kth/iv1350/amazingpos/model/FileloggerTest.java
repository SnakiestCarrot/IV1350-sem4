package se.kth.iv1350.amazingpos.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FileloggerTest {
    private Filelogger instanceToTest;
    private FileReader fileReader;
    private String fileName;


    @BeforeEach
    public void setUp() {
        fileName = "FileloggerTest.txt";
        instanceToTest = new Filelogger(fileName);      
    }

    @AfterEach
    public void tearDown() {
        instanceToTest = null;
        fileName = null;
    }

    @Test
    public void CreatesCorrectFileTest () {
        instanceToTest.log("Testing.");
        try {
            fileReader = new FileReader(fileName);
        }
        catch (FileNotFoundException exception){
            fail("Doesnt create correct file.");
        }
    }

    @Test
    public void WritesCorrectMessageTest () {
        String message = "Testing.";
        instanceToTest.log(message);

        StringBuilder builder = new StringBuilder();
        String str;
        String outcome;
        
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            while ((str = buffer.readLine()) != null) {
 
                builder.append(str);
            }
            outcome = builder.toString();
            buffer.close();

            assertTrue(outcome.equals(message), "Not writing correct message to file.");
        }

        catch (FileNotFoundException exception){
            fail("FileNotFoundException.");
        }
        catch (IOException exception) {
            fail("IOEXception");
        }  
    }

    @Test
    public void WritesCorrectMessageTest2 () {
        String message = "Testing2.";
        instanceToTest.log(message);

        StringBuilder builder = new StringBuilder();
        String str;
        String outcome;
        
        try {
            BufferedReader buffer = new BufferedReader(new FileReader(fileName));
            while ((str = buffer.readLine()) != null) {
 
                builder.append(str);
            }
            outcome = builder.toString();
            buffer.close();

            assertFalse(outcome.equals("Testing."), "Not writing correct message to file.");
        }

        catch (FileNotFoundException exception){
            fail("FileNotFoundException.");
        }
        catch (IOException exception) {
            fail("IOEXception");
        }  
    }
}
