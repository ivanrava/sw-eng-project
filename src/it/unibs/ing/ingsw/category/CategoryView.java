package it.unibs.ing.ingsw.category;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

import java.util.HashMap;
import java.util.Map;

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
                case 0 -> System.out.println("Uscita dal sistema");
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
        //versione solo nomi
        System.out.println(categoryController.allHierarchiesToString());
        //versione super verbose per testing
        //System.out.println(categoryController.getHierarchies());
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
        Map<String, Field> newFields = askFieldsForRoot();
        categoryController.makeRootCategory(nome, descrizione, newFields);
    }

    /**
     * Inserisce una nuova categoria figlia
     */
    private void insertChildCategory() {
        this.printHierarchies();
        // Chiediamo la radice
        String rootName = askAndCheckRootName();
        // Chiediamo il padre
        String parentName = askAndCheckParentName(rootName);
        // Chiediamo il nome
        String name = askAndCheckCategoryName(rootName);
        // Chiediamo la descrizione
        String description = InputDati.leggiStringaNonVuota("Inserisci il nome della descrizione: ");
        Map<String, Field> newFields = askFieldsForCategory(categoryController.searchTree(rootName, parentName));
        categoryController.makeChildCategory(rootName, parentName, name, description, newFields);
    }

    /**
     * Chiedi il nome della categoria radice (con controlli)
     * @return Nome della categoria radice
     */
    public String askAndCheckRootName(){
        String rootName;
        do {
            rootName = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria radice: ");
            if (!categoryController.existsRoot(rootName)) {
                System.out.println("Il nome non esiste :(");
            }
        } while (!categoryController.existsRoot(rootName));
        return rootName;
    }

    /**
     * Chiedi il nome della categoria genitore (con controlli)
     * @return Nome della categoria genitore
     */
    public String askAndCheckParentName(String rootName){
        String parentName;
        do {
            parentName = InputDati.leggiStringaNonVuota("Inserisci la categoria padre: ");
            if (!categoryController.exists(rootName, parentName)) {
                System.out.println("Il genitore non esiste :(");
            }
        } while (!categoryController.exists(rootName, parentName));
        return parentName;
    }

    /**
     * Chiedi il nome della categoria (con controlli)
     * @return Nome della categoria
     */
    public String askAndCheckCategoryName(String rootName){
        String name;
        do {
            name = InputDati.leggiStringaNonVuota("Inserisci il nome della categoria: ");
            if (categoryController.exists(rootName, name)) {
                System.out.println("Il nome della categoria è duplicato :(");
            }
        } while (categoryController.exists(rootName, name));
        return name;
    }

    /**
     * Chiedi i campi per una categoria radice
     * @return I campi per la categoria radice
     */
    public Map<String, Field> askFieldsForRoot() {
        return askFields(categoryController.getDefaultFields(), categoryController.getDefaultFields());
    }

    /**
     * Chiedi i campi per una categoria figlia
     * @param parent Categoria genitore
     * @return Campi della categoria figlia
     */
    public Map<String, Field> askFieldsForCategory(Category parent){
        return askFields(parent.getFields(), new HashMap<>());
    }

    /**
     * Chiedi i campi genericamente
     * @param controlMap Mappa di controllo
     * @param newFieldsMap Mappa dei nuovi campi
     * @return Mappa dei nuovi campi con quelli inseriti
     */
    public Map<String, Field> askFields(Map<String, Field> controlMap, Map<String, Field> newFieldsMap){
        boolean scelta;
        Map<String, Field> newMap = new HashMap<>(controlMap);
        do {
            scelta = InputDati.yesOrNo("Vuoi aggiungere un nuovo campo?");
            if (scelta){
                newMap.putAll(newFieldsMap);
                Field newField = createField(newMap);
                newFieldsMap.put(newField.getName(), newField);
            }
        }while (scelta);
        return newFieldsMap;
    }

    /**
     * Crea un nuovo campo
     * @param actualFields Campi già inseriti, usati per i controlli
     * @return Il nuovo campo inserito
     */
    public Field createField(Map<String, Field> actualFields){
        String fieldName;
        do{
            fieldName = InputDati.leggiStringaNonVuota("Nome del field: ");
            if (actualFields.containsKey(fieldName)){
                System.out.println("Field duplicato :-(");
            }
        }while (actualFields.containsKey(fieldName));

        boolean required = InputDati.yesOrNo("Obbligatorio");
        return new Field(required, fieldName);
    }

}