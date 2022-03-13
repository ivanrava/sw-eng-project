package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.Saves;

public class ConfiguratorView {
    private final CategoryView categoryView;
    private final ConfigView configView;

    public ConfiguratorView(Saves saves, Config configurazione) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(configurazione);
    }

    public void execute() {
        MyMenu mainMenu = new MyMenu("Interfaccia di amministrazione", new String[] {
                "Gestisci categorie",
                "Gestisci configurazione generale"
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.execute();
                // TODO: case 2
                case 2 -> configView.modifyConfig();
            }
        }while (scelta != 0);
    }

}
