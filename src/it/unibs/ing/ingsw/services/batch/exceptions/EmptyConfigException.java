package it.unibs.ing.ingsw.services.batch.exceptions;

public class EmptyConfigException extends Exception {
    public EmptyConfigException(String errorMessage) {
        super(errorMessage);
    }
}
