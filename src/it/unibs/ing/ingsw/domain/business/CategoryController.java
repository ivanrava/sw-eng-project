package it.unibs.ing.ingsw.domain.business;

import com.google.gson.JsonParseException;
import it.unibs.ing.ingsw.domain.business.exceptions.CategoryImportException;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.services.batch.JsonParser;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CategoryController {
    public static final String ASSERTION_ROOT_OVERWRITE = "Stai sovrascrivendo una radice";
    public static final String ASSERTION_PARENT_UNEXISTANT = "La categoria genitore fornita non esiste!";
    private final Map<String, Category> hierarchies;

    public CategoryController(DataContainer saves) {
        hierarchies = saves.getHierarchies();
    }

    public void importFromBatch(String filePath) throws CategoryImportException {
        try {
            JsonParser jsonParser = new JsonParser();
            Arrays.stream(jsonParser.readCategoriesJson(filePath)).forEach(category -> hierarchies.put(category.getName(), category));
        } catch (FileNotFoundException e) {
            throw new CategoryImportException("File non esistente...");
        } catch (JsonParseException e) {
            throw new CategoryImportException("Errore lettura file...");
        }
    }

    /**
     * Crea una categoria radice dai parametri forniti e la aggiunge alle gerarchie del sistema
     *
     * @param name        Nome della nuova categoria radice
     * @param description Descrizione della nuova categoria radice
     */
    public void makeRootCategory(String name, String description, Map<String, Field> newFields) {
        assert searchTree(name, name) == null : ASSERTION_ROOT_OVERWRITE;
        Category rootCategory = new Category(name, description, true, newFields);
        hierarchies.put(rootCategory.getName(), rootCategory);
    }

    /**
     * @return Campi di default delle categorie
     */
    public Map<String, Field> getDefaultFields() {
        return Category.getDefaultFields();
    }

    /**
     * Precondizione: la categoria genitore fornita deve esistere
     * <p>
     * Crea una categoria figlia dai parametri forniti
     *
     * @param rootName    Nome della categoria radice
     * @param parentName  Nome della categoria madre
     * @param name        Nome della nuova categoria
     * @param description Descrizione della nuova categoria
     * @param newFields   nuovi fields
     */
    public void makeChildCategory(String rootName, String parentName, String name, String description, Map<String, Field> newFields) {
        assert searchTree(rootName, parentName) != null : ASSERTION_PARENT_UNEXISTANT;
        Category parent = searchTree(rootName, parentName);
        parent.addChildCategory(new Category(name, description, newFields));
    }

    /**
     * Controlla se esiste già una categoria radice con il nome fornito
     *
     * @param rootName Nome della categoria radice
     * @return 'true' se il nome è duplicato, 'false' altrimenti
     */
    public boolean existsRoot(String rootName) {
        return exists(rootName, rootName);
    }

    /**
     * Controlla che la categoria fornita esista (nella gerarchia fornita)
     *
     * @param rootName Nome della categoria radice che identifica la gerarchia
     * @param name     Nome della categoria da cercare in quella gerarchia
     * @return 'true' se esiste già, 'false' altrimenti
     */
    public boolean exists(String rootName, String name) {
        return searchTree(rootName, name) != null;
    }

    /**
     * metodo per la ricerca di una categoria nella gerarchia
     *
     * @param rootName Nome della categoria radice in cui cercare
     * @param name     Nome della categoria da cercare
     * @return La Category cercata, se esiste. Altrimenti 'null'
     */
    public Category searchTree(String rootName, String name) {
        Category root = getRootCategory(rootName);
        if (root == null) return null;
        return root.searchTree(name);
    }

    /**
     * Ritorna la categoria radice con controlli case insensitive
     *
     * @param rootName Nome della categoria radice
     * @return Categoria radice dal nome dato
     */
    public Category getRootCategory(String rootName) {
        rootName = rootName.toUpperCase();
        return hierarchies.get(rootName);
    }

    public Set<String> getRootCategoryNames() {
        return hierarchies.keySet();
    }

    /**
     * controlla dato il nome di una categoria radice e un'altra categoria,
     * che la categoria sia foglia della gerarchia appartenente alla categoria
     * radice
     *
     * @param rootCategoryName nome categoria radice
     * @param leafCategoryName nome categoria foglia
     * @return 'true' se é una categoria foglia, 'false' altrimenti
     */
    public boolean isLeaf(String rootCategoryName, String leafCategoryName) {
        return searchTree(rootCategoryName, leafCategoryName).isLeaf();
    }

    /**
     * Ritorna i campi per una categoria
     *
     * @param rootCategoryName Nome della categoria radice a cui appartiene la categoria data
     * @param categoryName     Nome della categoria di cui prendere i campi
     * @return Campi della categoria, in una mappa nome-campo
     */
    public Map<String, Field> getFieldsForCategory(String rootCategoryName, String categoryName) {
        return searchTree(rootCategoryName, categoryName).getFields();
    }

    public Collection<Category> getRootCategories() {
        return hierarchies.values();
    }
}
