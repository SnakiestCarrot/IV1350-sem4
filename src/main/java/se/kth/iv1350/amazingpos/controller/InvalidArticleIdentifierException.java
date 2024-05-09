package se.kth.iv1350.amazingpos.controller;

public class InvalidArticleIdentifierException extends Exception{
        private int invalidIdentifier;

    public InvalidArticleIdentifierException(int invalidIdentifier){
        this.invalidIdentifier = invalidIdentifier;
    }

    public int getInvalidIdentifier() {
        return this.invalidIdentifier;
    }


}
