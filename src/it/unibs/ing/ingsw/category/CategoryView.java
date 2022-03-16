package it.unibs.ing.ingsw.category;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

import java.util.HashMap;
import java.util.Map;

public class CategoryView {
    public static final String MENU_TITLE = "Gestisci le categorie";
    public static final String[] VOCI = {
            "Aggiungi nuova categoria radice",
            "Aggiungi nuova categoria figlia",
            "Visualizza gerarchie"
    };
    public static final String BACK = "Ritorno al menu principale";
    public static final String ASSERTION_NEVER = "Il programma non dovrebbe mai arrivare qui!";
    public static final String INSERT_ROOT_NAME = "Inserisci il nome della nuova categoria radice: ";
    public static final String ERROR_NAME_DUPLICATE = "Nome non univoco :(";
    public static final String INSERT_DESCRIPTION = "Inserisci la descrizione: ";
    public static final String INSERT_PARENT_CATEGORY_NAME = "Inserisci la categoria padre: ";
    public static final String ERROR_PARENT_UNEXISTANT = "Il genitore non esiste :(";
    public static final String INSERT_CATEGORY_NAME = "Inserisci il nome della categoria: ";
    public static final String ASK_NEW_FIELD = "Vuoi aggiungere un nuovo campo?";
    public static final String ERROR_FIELD_DUPLICATE = "Field duplicato :-(";
    public static final String ASK_FIELD_REQUIRED = "Obbligatorio? ";
    public static final String INSERT_FIELD_NAME = "Nome del field: ";
    private final CategoryController categoryController;

    public CategoryView(Saves saves) {
        categoryController = new CategoryController(saves);
    }

    /**
     * Esegue l'UI principale per la gestione delle categorie
     */
    public void execute() {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, VOCI);
        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> insertRootCategory();
                case 2 -> insertChildCategory();
                case 3 -> printHierarchies();
                case 0 -> System.out.println(BACK);
                default -> {
                    assert false : ASSERTION_NEVER;
                }
            }
        } while (scelta != 0);
    }

    /**
     * Stampa tutte le gerarchie del sistema
     */
    public void printHierarchies() {
        //versione solo nomi
        System.out.println(categoryController.allHierarchiesToString());
        //versione super verbose per testing
        //System.out.println(categoryController.getHierarchies());
    }

    /**
     * Inserisce una nuova categoria radice
     * FIXME: c'è anche askRootCategoryName(), qualcosa non torna
     */
    private void insertRootCategory() {
        String nome, descrizione;
        do {
            nome = InputDati.leggiStringaNonVuota(INSERT_ROOT_NAME);
            if (categoryController.existsRoot(nome)) {
                System.out.println(ERROR_NAME_DUPLICATE);
            }
        } while (categoryController.existsRoot(nome));
        descrizione = InputDati.leggiStringaNonVuota(INSERT_DESCRIPTION);
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
        String description = InputDati.leggiStringaNonVuota(INSERT_DESCRIPTION);
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
            rootName = InputDati.leggiStringaNonVuota(INSERT_ROOT_NAME);
            if (!categoryController.existsRoot(rootName)) {
                System.out.println(ERROR_NAME_DUPLICATE);
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
            parentName = InputDati.leggiStringaNonVuota(INSERT_PARENT_CATEGORY_NAME);
            if (!categoryController.exists(rootName, parentName)) {
                System.out.println(ERROR_PARENT_UNEXISTANT);
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
            name = InputDati.leggiStringaNonVuota(INSERT_CATEGORY_NAME);
            if (categoryController.exists(rootName, name)) {
                System.out.println(ERROR_NAME_DUPLICATE);
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
            scelta = InputDati.yesOrNo(ASK_NEW_FIELD);
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
            fieldName = InputDati.leggiStringaNonVuota(INSERT_FIELD_NAME);
            if (actualFields.containsKey(fieldName)){
                System.out.println(ERROR_FIELD_DUPLICATE);
            }
        }while (actualFields.containsKey(fieldName));

        boolean required = InputDati.yesOrNo(ASK_FIELD_REQUIRED);
        return new Field(required, fieldName);
    }

}