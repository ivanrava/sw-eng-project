package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.domain.business.Category;

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
