package se.kth.iv1350.amazingpos.model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class that prints log messages to a file in current directory and
 * file will be called log.txt..
 * 
 * Taken from Leif Lindbäcks Object Oriented Developement page 196.
 */
public class Filelogger {
    private PrintWriter logStream;

    /**
     * Creates instance of logger and file. Deletes old file.
     */
    public Filelogger (String filename) {
        try {
            logStream = new PrintWriter
            (new FileWriter(filename), true);
        } catch (IOException ioe) {
            System.out.println("CAN NOT LOG.");
            ioe.printStackTrace();
        }
    }

    /**
     * Prints message to log file.
     * @param message The message thats printed.
     */
    public void log (String message) {
        logStream.println(message);
    }
}
