package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.io.Saves;

public class CustomerView {
    private CategoryView categoryView;

    public CustomerView(Saves saves) {
        categoryView = new CategoryView(saves);
    }

    public void execute(){
        MyMenu mainMenu = new MyMenu("Benvenuto Utente", new String[] {
                "Visualizza categorie",
                "Visualizza generalità"
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.printHierarchies();
            }
        }while (scelta != 0);
    }
}
