package it.unibs.ing.ingsw.category;

import it.unibs.ing.fp.mylib.MyMenu;

import java.util.Map;

public class CategoryView {
    private final CategoryController categoryController;

    public CategoryView() {
        categoryController = new CategoryController();
    }

    public void execute() {
        menu();
    }

    private void menu() {
        MyMenu mainMenu = new MyMenu("Main menu", new String[] {
                "Aggiungi nuova categoria radice",
                "Aggiungi nuova categoria figlia",
                "Visualizza gerarchie"
        });
        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1:
                    // TODO:
                    break;
                case 2:
                    // TODO:
                    break;
                case 3:
                    printHierarchies();
                    break;
                default:
            }
        } while (scelta != 0);
    }

    private void printHierarchies() {
        for (Map.Entry<String, Category> hierarchy : categoryController.getHierarchies().entrySet()) {
            System.out.println(hierarchy.getValue().toString());
        }
    }
}
