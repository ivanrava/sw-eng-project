package it.unibs.ing.ingsw.category;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CategoryController {
    private final Map<String, Category> hierarchies;

    public CategoryController() {
        hierarchies = new HashMap<>();
        // TODO: rimuovere test
        Category libro = new Category("Libro", "Opera cartacea", true);
        hierarchies.put("Libro", libro);
        hierarchies.put("Veicoli", new Category("Veicoli", "Brum brum", true));
        libro.addChildCategory(new Category("Romanzo", "Figo"));
    }

    /**
     * Crea una categoria radice dai parametri forniti e la aggiunge alle gerarchie del sistema
     * @param name Nome della nuova categoria radice
     * @param description Descrizione della nuova categoria radice
     */
    // TODO: aggiungere campi nativi
    public void makeRootCategory(String name, String description) {
        if (hierarchies.get(name) != null) {
            return;
        }
        Category rootCategory = new Category(name, description, true);
        hierarchies.put(name, rootCategory);
    }

    /**
     * Precondizione: la categoria genitore fornita deve esistere
     *
     * Crea una categoria figlia dai parametri forniti
     * @param rootName Nome della categoria radice
     * @param parentName Nome della categoria madre
     * @param name Nome della nuova categoria
     * @param description Descrizione della nuova categoria
     */
    // TODO: aggiungere campi nativi
    public void makeChildCategory(String rootName, String parentName, String name, String description) {
        assert searchTree(rootName, parentName) != null : "La categoria genitore fornita non esiste!";
        Category parent = searchTree(rootName, parentName);
        parent.addChildCategory(new Category(name, description));
    }

    /**
     * Controlla se esiste già una categoria radice con il nome fornito
     * @param name Nome della categoria radice
     * @return 'true' se il nome è duplicato, 'false' altrimenti
     */
    public boolean existsRoot(String name) {
        return hierarchies.get(name) != null;
    }

    /**
     * Controlla che la categoria fornita esista (nella gerarchia fornita)
     * @param rootName Nome della categoria radice che identifica la gerarchia
     * @param name Nome della categoria da cercare in quella gerarchia
     * @return 'true' se esiste già, 'false' altrimenti
     */
    public boolean exists(String rootName, String name) {
        return searchTree(rootName, name) != null;
    }

    /**
     * Ritorna tutte le gerarchie del sistema
     * @return Collection delle gerarchie del sistema
     */
    public Collection<Category> getHierarchies() {
        return hierarchies.values();
    }

    /**
     * Precondizione: la categoria radice fornita deve esistere
     *
     * @param rootName Nome della categoria radice in cui cercare
     * @param name Nome della categoria da cercare
     * @return La Category cercata, se esiste. Altrimenti 'null'
     */
    private Category searchTree(String rootName, String name) {
        assert existsRoot(name) : "La categoria radice fornita non esiste!";
        Category root = hierarchies.get(rootName);
        if (rootName.equals(name)) {
            return root;
        }
        return searchTree(root, name);
    }

    /**
     * Metodo ricorsivo per la ricerca nell'albero
     *
     * @param category Category da cui parte la ricerca
     * @param name Nome della categoria da cercare
     * @return La Category cercata, se esiste. Altrimenti 'null'
     */
    private Category searchTree(Category category, String name) {
        for (Map.Entry<String, Category> child : category.getChildren().entrySet()) {
            if (child.getValue().getName().equals(name)) {
                return child.getValue();
            } else if (!child.getValue().isLeaf()) {
                return searchTree(child.getValue(), name);
            }
        }
        return null;
    }
}
