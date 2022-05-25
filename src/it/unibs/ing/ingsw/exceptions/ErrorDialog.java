package it.unibs.ing.ingsw.exceptions;

public class ErrorDialog {

    public static void print(Throwable e) {
        System.out.printf("Errore: %s\n", e.getMessage());
    }

}
