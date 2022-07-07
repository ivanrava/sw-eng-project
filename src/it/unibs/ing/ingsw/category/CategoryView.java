package it.unibs.ing.ingsw.category;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CategoryView extends AbstractView {
    private static final String INSERT_LEAF_CATEGORY_NAME = "Inserisci il nome della categoria foglia: ";
    private static final String ERROR_NOT_LEAF = "Non è una categoria foglia :(";
    private static final String ERROR_CATEGORY_UNEXISTANT = "La categoria non esiste :(";
    private static final String INSERT_CATEGORY_NAME = "Inserisci il nome della categoria: ";
    private static final String ERROR_PARENT_UNEXISTANT = "Il genitore non esiste :(";
    private static final String INSERT_PARENT_CATEGORY_NAME = "Inserisci la categoria padre: ";
    private static final String INSERT_FIELD_NAME = "Nome del field: ";
    private static final String ASK_FIELD_REQUIRED = "Obbligatorio? ";
    private static final String ERROR_FIELD_DUPLICATE = "Field duplicato :-(";
    private static final String ASK_NEW_FIELD = "Vuoi aggiungere un nuovo campo?";
    private static final String INSERT_DESCRIPTION = "Inserisci la descrizione: ";
    private static final String ERROR_NAME_DUPLICATE = "Nome non univoco :(";
    private static final String ASK_ROOT_CATEGORY_NAME = "Inserisci il nome della categoria radice: ";
    public static final String INSERT_FILEPATH = "Inserisci il percorso assoluto del file: ";
    private static final String ASK_FIELD_FACOLTATIVO = "%s (facoltativo): ";
    private static final String ASK_FIELD_OBBLIGATORIO = "%s (obbligatorio): ";

    protected String MENU_TITLE = "Gestione categorie";

    protected CategoryView(InputProvider inputProvider) {
        super(inputProvider);
    }

    /**
     * Stampa tutte le gerarchie del sistema
     */
    public void printHierarchies(Collection<Category> rootCategories) {
        System.out.println(renderAll(rootCategories));
    }

    public String askDescription() {
        return inputProvider.leggiStringaNonVuota(INSERT_DESCRIPTION);
    }

    public String askRootName(Set<String> rootCategoryNames) {
        String name;
        do {
            name = inputProvider.leggiStringaNonVuota(ASK_ROOT_CATEGORY_NAME);
            if (rootCategoryNames.contains(name)) {
                System.out.println(ERROR_NAME_DUPLICATE);
            }
        } while (rootCategoryNames.contains(name));
        return name;
    }

    /**
     * Chiedi il nome della categoria genitore (con controlli)
     *
     * @param rootName Nome della categoria radice
     * @return Nome della categoria genitore
     */
    public String askAndCheckParentName(String rootName, CategoryMVController controller) {
        String parentName;
        do {
            parentName = inputProvider.leggiStringaNonVuota(INSERT_PARENT_CATEGORY_NAME);
            if (!controller.exists(rootName, parentName)) {
                System.out.println(ERROR_PARENT_UNEXISTANT);
            }
        } while (!controller.exists(rootName, parentName));
        return parentName;
    }

    /**
     * Chiedi il nome della categoria (con controlli)
     *
     * @param rootName Nome della categoria radice
     * @return Nome della categoria
     */
    public String askAndCheckCategoryName(String rootName, CategoryMVController controller) {
        String name;
        do {
            name = inputProvider.leggiStringaNonVuota(INSERT_CATEGORY_NAME);
            if (controller.exists(rootName, name)) {
                System.out.println(ERROR_NAME_DUPLICATE);
            }
        } while (controller.exists(rootName, name));
        return name;
    }

    /**
     * Chiedi i campi per una categoria radice
     *
     * @return I campi per la categoria radice
     */
    public Map<String, Field> askFieldsForRoot(Map<String, Field> defaultFields) {
        return askFields(defaultFields, defaultFields);
    }

    /**
     * Chiedi i campi per una categoria figlia
     *
     * @param parent Categoria genitore
     * @return Campi della categoria figlia
     */
    public Map<String, Field> askFieldsForCategory(Category parent) {
        return askFields(parent.getFields(), new HashMap<>());
    }

    /**
     * Chiedi i campi genericamente
     *
     * @param controlMap   Mappa di controllo
     * @param newFieldsMap Mappa dei nuovi campi
     * @return Mappa dei nuovi campi con quelli inseriti
     */
    private Map<String, Field> askFields(Map<String, Field> controlMap, Map<String, Field> newFieldsMap) {
        boolean scelta;
        Map<String, Field> newMap = new HashMap<>(controlMap);
        do {
            scelta = inputProvider.yesOrNo(ASK_NEW_FIELD);
            if (scelta) {
                newMap.putAll(newFieldsMap);
                Field newField = createField(newMap);
                newFieldsMap.put(newField.name(), newField);
            }
        } while (scelta);
        return newFieldsMap;
    }

    /**
     * Crea un nuovo campo
     *
     * @param actualFields Campi già inseriti, usati per i controlli
     * @return Il nuovo campo inserito
     */
    private Field createField(Map<String, Field> actualFields) {
        String fieldName;
        do {
            fieldName = inputProvider.leggiStringaNonVuota(INSERT_FIELD_NAME);
            if (actualFields.containsKey(fieldName)) {
                System.out.println(ERROR_FIELD_DUPLICATE);
            }
        } while (actualFields.containsKey(fieldName));

        boolean required = inputProvider.yesOrNo(ASK_FIELD_REQUIRED);
        return new Field(required, fieldName);
    }

    public String askFilePath() {
        return inputProvider.leggiStringaNonVuota(INSERT_FILEPATH);
    }

    /**
     * Chiedi il nome di una categoria foglia
     *
     * @param rootName Nome della categoria radice
     * @return Nome della categoria foglia
     */
    public String askLeafName(String rootName, CategoryMVController controller) {
        String leafCategoryName;
        do {
            leafCategoryName = inputProvider.leggiStringaNonVuota(INSERT_LEAF_CATEGORY_NAME);
            if (!controller.exists(rootName, leafCategoryName)) {
                System.out.println(ERROR_CATEGORY_UNEXISTANT);
            } else if (!controller.isLeaf(rootName, leafCategoryName)) {
                System.out.println(ERROR_NOT_LEAF);
            }
        } while (!controller.exists(rootName, leafCategoryName) || !controller.isLeaf(rootName, leafCategoryName));
        return leafCategoryName;
    }

    /**
     * Chiede l'inserimento dei valori dei campi di una categoria
     *
     * @param categoryFields Campi richiesti da una categoria
     * @return Map che ha come chiave il nome del campo, e come valore il valore del campo
     */
    public Map<String, String> askFieldValues(Map<String, Field> categoryFields) {
        Map<String, String> fieldValues = new HashMap<>();
        String value;
        for (Map.Entry<String, Field> entry : categoryFields.entrySet()) {
            if (entry.getValue().required()) {
                value = inputProvider.leggiStringaNonVuota(String.format(ASK_FIELD_OBBLIGATORIO, entry.getKey()));
            } else {
                value = inputProvider.leggiStringa(String.format(ASK_FIELD_FACOLTATIVO, entry.getKey()));
            }
            fieldValues.put(entry.getKey(), value);
        }
        return fieldValues;
    }
}
