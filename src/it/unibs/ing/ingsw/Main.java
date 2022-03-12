package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Saves saves = new Saves();
            View view = new View(saves);
            view.execute();
            saves.save();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("La configurazione è presente, ma non riesco a caricarla!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
