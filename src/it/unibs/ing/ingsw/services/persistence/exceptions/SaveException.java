package it.unibs.ing.ingsw.services.persistence.exceptions;

public class SaveException extends Exception {
    public SaveException(String errorMessage) {
        super(errorMessage);
    }
}
