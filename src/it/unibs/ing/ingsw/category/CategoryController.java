package it.unibs.ing.ingsw.category;

import com.google.gson.JsonParseException;
import it.unibs.ing.ingsw.exceptions.CategoryImportException;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.io.batch.JsonParser;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class CategoryController {
    public static final String ASSERTION_ROOT_OVERWRITE = "Stai sovrascrivendo una radice";
    public static final String ASSERTION_ROOT_UNEXISTANT = "La categoria radice fornita non esiste!";
    public static final String ASSERTION_PARENT_UNEXISTANT = "La categoria genitore fornita non esiste!";
    private final Map<String, Category> hierarchies;

    public CategoryController(Saves saves) {
        hierarchies = saves.getHierarchies();
    }

    public void importFromBatch(String filePath) throws CategoryImportException {
        try {
            JsonParser jsonParser = new JsonParser();
            Arrays.stream(jsonParser.readCategoriesJson(filePath)).
                    forEach(category -> hierarchies.put(category.getName(), category));
        } catch (FileNotFoundException e) {
            throw new CategoryImportException("File non esistente...");
        }
        catch (JsonParseException e) {
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
     * Precondizione: la categoria radice fornita deve esistere
     *
     * @param rootName Nome della categoria radice in cui cercare
     * @param name     Nome della categoria da cercare
     * @return La Category cercata, se esiste. Altrimenti 'null'
     */
    public Category searchTree(String rootName, String name) {
        assert existsRoot(rootName) : ASSERTION_ROOT_UNEXISTANT;
        Category root = getRootCategoryCaseInsensitive(rootName);
        if (rootName.equalsIgnoreCase(name)) {
            return root;
        }
        return searchTree(root, name);
    }

    /**
     * Ritorna la categoria radice con controlli case insensitive
     *
     * @param rootName Nome della categoria radice
     * @return Categoria radice dal nome dato
     */
    public Category getRootCategoryCaseInsensitive(String rootName) {
        for (Map.Entry<String, Category> entry : hierarchies.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(rootName))
                return entry.getValue();
        }
        return null;
    }

    /**
     * Metodo ricorsivo per la ricerca nell'albero
     *
     * @param category Category da cui parte la ricerca
     * @param name     Nome della categoria da cercare
     * @return La Category cercata, se esiste. Altrimenti 'null'
     */
    private Category searchTree(Category category, String name) {
        for (Map.Entry<String, Category> child : category.getChildren().entrySet()) {
            if (child.getValue().getName().equalsIgnoreCase(name)) {
                return child.getValue();
            } else if (!child.getValue().isLeaf()) {
                if (searchTree(child.getValue(), name) != null) {
                    return searchTree(child.getValue(), name);
                }
            }
        }
        return null;
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
