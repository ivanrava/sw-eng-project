package it.unibs.ing.ingsw.ui.views;

public class ErrorDialog {

    public static void print(Exception e) {
        System.out.printf("%s\n", e.getMessage());
    }

}
