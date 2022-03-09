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

    public void makeChildCategory(String rootName, String parentName, String name, String description) {
        Category parent = searchTree(rootName, parentName);
        parent.addChildCategory(new Category(name, description));
    }

    public Category search(String name) {
        return hierarchies.get(name);
    }

    public boolean existsRoot(String name) {
        return search(name) != null;
    }

    public boolean exists(String rootName, String name) {
        return searchTree(rootName, name) != null;
    }

    public Map<String, Category> getHierarchies() {
        return hierarchies;
    }

    /**
     * Precondizione: rootName in hierarchies
     * @param rootName
     * @param name
     * @return
     */
    private Category searchTree(String rootName, String name) {
        if (!hierarchies.containsKey(rootName)) {
            return null; // TODO: throw
        }
        Category root = hierarchies.get(rootName);
        if (rootName.equals(name)) {
            return root;
        }
        return searchTree(root, name);
    }

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
