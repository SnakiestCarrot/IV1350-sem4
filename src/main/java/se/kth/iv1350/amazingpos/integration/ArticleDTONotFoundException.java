package se.kth.iv1350.amazingpos.integration;

/**
 * Thrown when articleDTO is not found in article catalog handler.
 */
public class ArticleDTONotFoundException extends Exception{
    private int invalidIdentifier;

    /**
     * Creates a new instance of exception object. Saves the identifier that was invalid.
     * @param invalidIdentifier
     */
    public ArticleDTONotFoundException (int invalidIdentifier) {
        this.invalidIdentifier = invalidIdentifier;
    }

    public int getInvalidIdentifier () {
        return this.invalidIdentifier;
    }
}
