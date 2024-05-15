package se.kth.iv1350.amazingpos.integration;

/**
 * Exception made when database cant be called for some reason.
 */
public class DatabaseFailureException extends Exception {

    /**
     * Creates an instance of exception.
     */
    public DatabaseFailureException () {
        super("Unable to connect to database.");
    }

}
