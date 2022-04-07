package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.io.batch.JsonParser;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.View;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final String ERROR_LOAD_CONFIG = "La configurazione è presente, ma non riesco a caricarla!";

    /**
     * Entrypoint dell'applicazione
     * @param args Argomenti a linea di comando
     */
    public static void main(String[] args) {
        try {
            Saves saves = new Saves();
            View view = new View(saves);
            view.execute();
            saves.save();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(ERROR_LOAD_CONFIG);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
