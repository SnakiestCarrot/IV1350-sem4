package se.kth.iv1350.amazingpos.controller;

/**
 * Generic exception throwwn from controller when an operation from view failed,
 * like a database failure.
 */
public class OperationFailedException extends Exception {

    /**
     * Creates an instance of the exception.
     * @param message A message of what happened
     * @param cause The original exception that caused this.
     */
    public OperationFailedException (String message, Exception cause) {
        super(message, cause);
    }
}
