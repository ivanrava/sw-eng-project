package it.unibs.ing.ingsw.domain.business.exceptions;

public class CategoryImportException extends Exception {
    public CategoryImportException(String errorMessage) {
        super(errorMessage);
    }
}
