package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.category.Category;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveHierarchies implements Serializable {
    public static final String SAVE_HIERARCHIES = "./hierarchiesRegister.dat";
    private final Map<String, Category> hierarchies = new HashMap<>();

    public Map<String, Category> getHierarchies() {
        return hierarchies;
    }

    public static SaveHierarchies loadHierarchies() throws IOException, ClassNotFoundException {
        File f = new File(SAVE_HIERARCHIES);
        if (f.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(f)))) {
                return (SaveHierarchies) inputStream.readObject();
            }
        }
        return new SaveHierarchies();
    }

    public void saveHierarchies() throws IOException {
        File f = new File(SAVE_HIERARCHIES);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            outputStream.writeObject(this);
        }
    }

}
