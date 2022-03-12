package it.unibs.ing.ingsw.category;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

public class CategoryView {
    private final CategoryController categoryController;

    public CategoryView(Saves saves) {
        categoryController = new CategoryController(saves);
    }


    /**
     * Esegue l'UI principale per la gestione delle categorie
     */
    public void execute() {
        MyMenu mainMenu = new MyMenu("Main menu", new String[] {
                "Aggiungi nuova categoria radice",
                "Aggiungi nuova categoria figlia",
                "Visualizza gerarchie"
        });
        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> insertRootCategory();
                case 2 -> insertChildCategory();
                case 3 -> printHierarchies();
                default -> {
                    assert false : "Il programma non dovrebbe mai arrivare qui!";
                }
            }
        } while (scelta != 0);
    }

    /**
     * Stampa tutte le gerarchie del sistema
     */
    private void printHierarchies() {
        System.out.println(categoryController.allHierarchiesToString());
    }

    /**
     * Inserisce una nuova categoria radice
     */
    private void insertRootCategory() {
        String nome, descrizione;
        do {
            nome = InputDati.leggiStringaNonVuota("Inserisci il nome della nuova categoria radice: ");
            if (categoryController.existsRoot(nome)) {
                System.out.println("Nome non univoco :(");
            }
        } while (categoryController.existsRoot(nome));
        descrizione = InputDati.leggiStringaNonVuota("Inserisci la descrizione della nuova categoria radice: ");
        categoryController.makeRootCategory(nome, descrizione);
    }

    /**
     * Inserisce una nuova categoria figlia
     */
    private void insertChildCategory() {
        this.printHierarchies();
        // Chiediamo la radice
        String rootName;
        do {
            rootName = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria radice: ");
            if (!categoryController.existsRoot(rootName)) {
                System.out.println("Il nome non esiste :(");
            }
        } while (!categoryController.existsRoot(rootName));
        // Chiediamo il padre
        String parentName;
        do {
            parentName = InputDati.leggiStringaNonVuota("Inserisci la categoria padre: ");
            if (!categoryController.exists(rootName, parentName)) {
                System.out.println("Il genitore non esiste :(");
            }
        } while (!categoryController.exists(rootName, parentName));
        // Chiediamo il nome
        String name;
        do {
            name = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria: ");
            if (categoryController.exists(rootName, name)) {
                System.out.println("Il nome della categoria è duplicato :(");
            }
        } while (categoryController.exists(rootName, name));
        // Chiediamo la descrizione
        String description = InputDati.leggiStringaNonVuota("Inserisci il nome della descrizione: ");
        categoryController.makeChildCategory(rootName, parentName, name, description);
    }
}