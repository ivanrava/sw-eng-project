package it.unibs.ing.ingsw.category;

import java.util.HashMap;
import java.util.Map;

public class CategoryController {
    private final Map<String, Category> hierarchies;

    public CategoryController() {
        hierarchies = new HashMap<>();
        // Test FIXME:
        Category libro = new Category("Libro", "Opera cartacea", true);
        hierarchies.put("Libro", libro);
        hierarchies.put("Veicoli", new Category("Veicoli", "Brum brum", true));
        libro.addChildCategory(new Category("Romanzo", "Figo"));
    }

    public boolean makeRootCategory(String name, String description) {
        if (hierarchies.get(name) != null) {
            return false;
        }
        Category rootCategory = new Category(name, description, true);
        hierarchies.put(name, rootCategory);
        return true;
    }

    public boolean makeChildCategory(String name, String description, String parentName) {
        return true;
        // FIXME:
    }

    public Category search(String name) {
        return hierarchies.get(name);
    }

    public boolean existsRoot(String name) {
        return search(name) != null;
    }

    public Map<String, Category> getHierarchies() {
        return hierarchies;
    }
}
