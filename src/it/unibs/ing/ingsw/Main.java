package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.exceptions.ErrorDialog;
import it.unibs.ing.ingsw.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.exceptions.SaveException;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.View;

import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    /**
     * Entrypoint dell'applicazione
     *
     * @param args Argomenti a linea di comando
     */
    public static void main(String[] args) {
        try {
            System.getProperties().load(new FileInputStream("./system.properties"));
            Saves saves = new Saves();
            View view = new View(saves);
            view.execute();
            saves.save();
        } catch (LoadSavesException | SaveException | IOException e) {
            ErrorDialog.print(e);
            System.exit(1);
        }
    }
}
