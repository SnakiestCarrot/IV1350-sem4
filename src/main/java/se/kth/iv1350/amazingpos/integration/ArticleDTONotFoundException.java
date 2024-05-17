package se.kth.iv1350.amazingpos.integration;

/**
 * When an articleDTO was not found in the article catalog handler, this exception is thrown.
 * Saves the identifier that was entered when this exception occured.
 */
public class ArticleDTONotFoundException extends Exception{
    private int invalidIdentifier;

    /**
     * Creates a new instance of exception object.
     * @param invalidIdentifier Identifier that was used.
     */
    public ArticleDTONotFoundException (int invalidIdentifier) {
        super("An articleDTO was not found in the catalog. Identifier that was used: " + invalidIdentifier);
        this.invalidIdentifier = invalidIdentifier;
    }

    public int getInvalidIdentifier () {
        return this.invalidIdentifier;
    }
}
