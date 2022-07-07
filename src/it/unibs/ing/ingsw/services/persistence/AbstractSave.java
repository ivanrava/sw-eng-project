package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.services.persistence.exceptions.SaveException;

import java.io.*;

public abstract class AbstractSave<T> implements Saveable {
    private final T toSave;
    private final String filename;

    public AbstractSave(String filename) throws IOException, ClassNotFoundException {
        this.filename = filename;
        File f = new File(filename);
        f.getParentFile().mkdirs();
        if (f.exists()) {
            toSave = load();
        } else {
            toSave = defaultSaveObject();
        }
    }

    protected final T getSaveObject() {
        return toSave;
    }

    protected abstract T defaultSaveObject();

    protected T load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(filename)))) {
            return (T) inputStream.readObject();
        }
    }

    public void save() throws SaveException {
        File f = new File(filename);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            outputStream.writeObject(toSave);
        } catch (IOException e) {
            throw new SaveException(e.getMessage());
        }
    }
}
