package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.business.ArticleController;
import it.unibs.ing.ingsw.domain.business.CategoryController;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.business.Field;
import it.unibs.ing.ingsw.domain.business.exceptions.CategoryImportException;
import it.unibs.ing.ingsw.ui.views.ErrorDialog;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.ui.views.AbstractView;
import it.unibs.ing.ingsw.ui.views.CategoryView;

import java.util.LinkedHashMap;
import java.util.Map;

public class CategoryMVController extends AbstractMVController {
    public static final String ADD_ROOT_CATEGORY = "Aggiungi nuova categoria radice";
    public static final String ADD_CHILD_CATEGORY = "Aggiungi nuova categoria figlia";
    public static final String PRINT_HIERARCHIES = "Visualizza gerarchie";
    public static final String IMPORT_FROM_FILE = "Importa da file";
    public static final String SUCCESS_IMPORT = "Importazione avvenuta con successo :-)";
    private final CategoryController categoryController;
    private final ArticleController articleController;
    private final CategoryView categoryView;

    public CategoryMVController(DataContainer saves, InputProvider inputProvider) {
        categoryController = new CategoryController(saves);
        articleController = new ArticleController(saves);
        categoryView = new CategoryView(inputProvider);
    }

    @Override
    protected boolean beforeExecute() {
        return true;
    }

    @Override
    protected LinkedHashMap<String, Runnable> getMenuOptions(User user) {
        LinkedHashMap<String, Runnable> menuOptions = new LinkedHashMap<>();
        menuOptions.put(ADD_ROOT_CATEGORY, this::insertRootCategory);
        menuOptions.put(ADD_CHILD_CATEGORY, this::insertChildCategory);
        menuOptions.put(PRINT_HIERARCHIES, this::printHierarchies);
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
        String nome = categoryView.askNewRootName(this);
        String descrizione = categoryView.askDescription();
        Map<String, Field> newFields = categoryView.askFieldsForRoot(categoryController.getDefaultFields());
        categoryController.makeRootCategory(nome, descrizione, newFields);
    }

    /**
     * Inserisce una nuova categoria figlia
     */
    private void insertChildCategory() {
        this.printHierarchies();
        String rootName = categoryView.askRootName(this);
        String parentName = categoryView.askAndCheckParentName(rootName, this);
        String categoryName = categoryView.askAndCheckCategoryName(rootName, this);
        String description = categoryView.askDescription();
        Map<String, Field> newFields = categoryView.askFieldsForCategory(categoryController.searchTree(rootName, parentName));
        categoryController.makeChildCategory(rootName, parentName, categoryName, description, newFields);
    }


    public String askRootName() {
        return categoryView.askRootName(this);
    }

    public String askLeafName(String rootName) {
        return categoryView.askLeafName(rootName, this);
    }

    public Map<String, String> askFieldValues(String rootCategoryName, String leafCategoryName) {
        return categoryView.askFieldValues(categoryController.getFieldsForCategory(rootCategoryName, leafCategoryName));
    }

    public boolean exists(String rootName, String parentName) {
        return categoryController.exists(rootName, parentName);
    }

    public boolean existsRoot(String rootName) {
        return categoryController.existsRoot(rootName);
    }

    public boolean isLeaf(String rootName, String leafCategoryName) {
        return categoryController.isLeaf(rootName, leafCategoryName);
    }
}