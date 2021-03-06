package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.domain.auth.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveUsers extends AbstractSave<Map<String, User>> {
    public SaveUsers(String filename) throws IOException, ClassNotFoundException {
        super(filename);
    }


    public Map<String, User> getUsers() {
        return getSaveObject();
    }

    @Override
    public Map<String, User> defaultSaveObject() {
        return new HashMap<>();
    }
}
