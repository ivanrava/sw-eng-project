package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.Saves;

public class ConfiguratorView {
    private static final String MENU_TITLE = "Interfaccia di amministrazione";
    private static final String MANAGE_CATEGORIES = "Gestisci categorie";
    private static final String MANAGE_CONFIG = "Gestisci configurazione generale";
    private final CategoryView categoryView;
    private final ConfigView configView;

    public ConfiguratorView(Saves saves) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(saves);
    }

    /**
     * Esegui l'UI specifica del configuratore
     */
    public void execute() {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, new String[] {
                MANAGE_CATEGORIES,
                MANAGE_CONFIG
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.execute();
                case 2 -> configView.execute();
            }
        }while (scelta != 0);
    }

}
