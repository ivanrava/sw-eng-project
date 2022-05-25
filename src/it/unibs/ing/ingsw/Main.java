package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.exceptions.ErrorDialog;
import it.unibs.ing.ingsw.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.exceptions.SaveException;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.View;

public class Main {

    /**
     * Entrypoint dell'applicazione
     *
     * @param args Argomenti a linea di comando
     */
    public static void main(String[] args) {
        try {
            Saves saves = new Saves();
            View view = new View(saves);
            view.execute();
            saves.save();
        } catch (LoadSavesException | SaveException e) {
            ErrorDialog.print(e);
            System.exit(1);
        }
    }
}
