package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.io.Saves;

public class ConfiguratorView {
    private final CategoryView categoryView;

    public ConfiguratorView(Saves saves) {
        categoryView = new CategoryView(saves);
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
            }
        }while (scelta != 0);
    }
}
