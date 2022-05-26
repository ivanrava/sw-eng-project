package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.exceptions.SaveException;

import java.io.*;

public abstract class AbstractSave<T> implements Saveable {
    private final T toSave;

    public AbstractSave() throws IOException, ClassNotFoundException {
        File f = new File(getSaveFilename());
        if (f.exists()) {
            toSave = load();
        } else {
            toSave = defaultSaveObject();
        }
    }

    protected abstract String getSaveFilename();

    protected final T getSaveObject() {
        return toSave;
    }

    protected abstract T defaultSaveObject();

    protected T load() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(getSaveFilename())))) {
            return (T) inputStream.readObject();
        }
    }

    public void save() throws SaveException {
        File f = new File(getSaveFilename());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            outputStream.writeObject(toSave);
        } catch (IOException e) {
            throw new SaveException(e.getMessage());
        }
    }
}
