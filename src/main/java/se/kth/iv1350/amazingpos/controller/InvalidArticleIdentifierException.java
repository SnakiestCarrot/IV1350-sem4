package se.kth.iv1350.amazingpos.controller;

/**
 * If the identifier entered in view could not be found in the database, this is error is sent from the controller
 * to the view.
 */
public class InvalidArticleIdentifierException extends Exception{
        private int invalidIdentifier;

    /**
     * Creates an instance of the exception. 
     * @param invalidIdentifier The identifier that was invalid.
     */
    public InvalidArticleIdentifierException(int invalidIdentifier){
        this.invalidIdentifier = invalidIdentifier;
    }

    public int getInvalidIdentifier() {
        return this.invalidIdentifier;
    }


}
