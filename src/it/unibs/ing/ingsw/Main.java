package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.exceptions.ErrorDialog;
import it.unibs.ing.ingsw.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.exceptions.SaveException;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.AppController;

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
            DataContainer saves = new Saves();
            AppController appController = new AppController(saves);
            appController.execute();
            saves.save();
        } catch (LoadSavesException | SaveException | IOException e) {
            ErrorDialog.print(e);
            System.exit(1);
        }
    }
}
