package it.unibs.ing.ingsw.category;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.ingsw.article.ArticleController;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.exceptions.CategoryImportException;
import it.unibs.ing.ingsw.exceptions.ErrorDialog;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.ui.AbstractMVController;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.util.Map;

public class CategoryMVController extends AbstractMVController {
    public static final String ADD_ROOT_CATEGORY = "Aggiungi nuova categoria radice";
    public static final String ADD_CHILD_CATEGORY = "Aggiungi nuova categoria figlia";
    public static final String PRINT_HIERARCHIES = "Visualizza gerarchie";
    public static final String IMPORT_FROM_FILE = "Importa da file";
    public static final String ERROR_CATEGORY_UNEXISTANT = "La categoria non esiste :(";
    public static final String ERROR_NOT_LEAF = "Non è una categoria foglia :(";
    public static final String INSERT_LEAF_CATEGORY_NAME = "Inserisci il nome della categoria foglia: ";
    public static final String SUCCESS_IMPORT = "Importazione avvenuta con successo :-)";
    private final CategoryController categoryController;
    private final ArticleController articleController;
    private final CategoryView categoryView;

    public CategoryMVController(DataContainer saves) {
        categoryController = new CategoryController(saves);
        articleController = new ArticleController(saves);
        categoryView = new CategoryView();
    }

    @Override
    protected void beforeExecute() {
    }

    @Override
    protected Map<String, Runnable> getMenuOptions(User user) {
        Map<String, Runnable> menuOptions = new java.util.HashMap<>(Map.of(ADD_ROOT_CATEGORY, this::insertRootCategory, ADD_CHILD_CATEGORY, this::insertChildCategory, PRINT_HIERARCHIES, this::printHierarchies));
        if (articleController.emptyArticles()) menuOptions.put(IMPORT_FROM_FILE, this::importFromBatch);
        return menuOptions;
    }

    @Override
    protected AbstractView getView() {
        return categoryView;
    }

    /**
     * Stampa tutte le gerarchie del sistema
     */
    public void printHierarchies() {
        categoryView.printHierarchies(categoryController.getRootCategories());
    }

    private void importFromBatch() {
        assert articleController.emptyArticles();
        try {
            String filePath = categoryView.askFilePath();
            categoryController.importFromBatch(filePath);
            categoryView.message(SUCCESS_IMPORT);
        } catch (CategoryImportException e) {
            ErrorDialog.print(e);
        }
    }


    /**
     * Inserisce una nuova categoria radice
     */
    private void insertRootCategory() {
        String nome = categoryView.askRootName(categoryController.getRootCategoryNames());
        String descrizione = categoryView.askDescription();
        Map<String, Field> newFields = categoryView.askFieldsForRoot(categoryController.getDefaultFields());
        categoryController.makeRootCategory(nome, descrizione, newFields);
    }

    /**
     * Inserisce una nuova categoria figlia
     */
    private void insertChildCategory() {
        this.printHierarchies();
        // Chiediamo la radice
        String rootName = categoryView.askRootName(categoryController.getRootCategoryNames());
        // Chiediamo il padre
        String parentName = categoryView.askAndCheckParentName(rootName, this);
        // Chiediamo il nome
        String name = categoryView.askAndCheckCategoryName(rootName, this);
        // Chiediamo la descrizione
        String description = categoryView.askDescription();
        Map<String, Field> newFields = categoryView.askFieldsForCategory(categoryController.searchTree(rootName, parentName));
        categoryController.makeChildCategory(rootName, parentName, name, description, newFields);
    }


    /**
     * Chiedi il nome di una categoria foglia
     *
     * @param rootName Nome della categoria radice
     * @return Nome della categoria foglia
     */
    public String askAndCheckLeafName(String rootName) {
        String leafCategoryName;
        do {
            leafCategoryName = InputDati.leggiStringaNonVuota(INSERT_LEAF_CATEGORY_NAME);
            if (!categoryController.exists(rootName, leafCategoryName)) {
                System.out.println(ERROR_CATEGORY_UNEXISTANT);
            } else if (!categoryController.isLeaf(rootName, leafCategoryName)) {
                System.out.println(ERROR_NOT_LEAF);
            }
        } while (!categoryController.exists(rootName, leafCategoryName) || !categoryController.isLeaf(rootName, leafCategoryName));
        return leafCategoryName;
    }


    public boolean exists(String rootName, String parentName) {
        return categoryController.exists(rootName, parentName);
    }
}