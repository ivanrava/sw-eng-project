package it.unibs.ing.ingsw.exceptions;

public class WrongCredentialsException extends Exception {
    public WrongCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
