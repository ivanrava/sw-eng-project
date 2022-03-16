package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.Saves;

public class CustomerView {
    private static final String MENU_TITLE = "Benvenuto Utente";
    private static final String SEE_CATEGORIES = "Visualizza categorie";
    private static final String SEE_CONFIG = "Visualizza generalità";
    private final CategoryView categoryView;
    private final ConfigView configView;

    public CustomerView(Saves saves) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(saves);
    }

    /**
     * Esegui l'UI del fruitore
     */
    public void execute() {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, new String[] {
                SEE_CATEGORIES,
                SEE_CONFIG
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.printHierarchies();
                case 2 -> configView.printConfig();
            }
        }while (scelta != 0);
    }
}
