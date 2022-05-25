package it.unibs.ing.ingsw.exceptions;

public class WrongCredentialsException extends Throwable {
    public WrongCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
