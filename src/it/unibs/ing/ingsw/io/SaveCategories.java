package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.category.Category;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveCategories extends AbstractSave<Map<String, Category>> {

    public SaveCategories(String filename) throws IOException, ClassNotFoundException {
        super(filename);
    }

    public Map<String, Category> getHierarchies() {
        return getSaveObject();
    }

    @Override
    public Map<String, Category> defaultSaveObject() {
        return new HashMap<>();
    }
}
