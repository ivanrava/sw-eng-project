package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.category.Category;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveHierarchies extends AbstractSave<Map<String, Category>> {
    public static final String SAVE_HIERARCHIES = "./hierarchiesRegister.dat";

    public SaveHierarchies() throws IOException, ClassNotFoundException {}

    @Override
    protected String getSaveFilename() {
        return SAVE_HIERARCHIES;
    }

    public Map<String, Category> getHierarchies() {
        return getSaveObject();
    }

    @Override
    public Map<String, Category> defaultSaveObject() {
        return new HashMap<>();
    }
}
