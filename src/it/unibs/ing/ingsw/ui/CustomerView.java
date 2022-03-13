package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.Saves;

public class CustomerView {
    private final CategoryView categoryView;
    private final ConfigView configView;

    public CustomerView(Saves saves, Config configurazione) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(configurazione);
    }


    public void execute() {
        MyMenu mainMenu = new MyMenu("Benvenuto Utente", new String[] {
                "Visualizza categorie",
                "Visualizza generalità"
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.printHierarchies();
                // TODO: case 2
                case 2 -> configView.printConfig();
            }
        }while (scelta != 0);
    }
}
