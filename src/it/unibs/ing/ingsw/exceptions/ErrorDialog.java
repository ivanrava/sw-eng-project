package it.unibs.ing.ingsw.exceptions;

public class ErrorDialog {

    public static void print(Exception e) {
        System.out.printf("%s\n", e.getMessage());
    }

}
