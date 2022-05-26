package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.auth.User;

import java.io.*;
import java.util.Collections;
import java.util.Map;

public class SaveUsers extends AbstractSave<Map<String, User>> {
    public static final String CONFIG_SAVE_FILEUSER = "./usersRegister.dat";

    public SaveUsers() throws IOException, ClassNotFoundException {}

    @Override
    protected String getSaveFilename() {
        return CONFIG_SAVE_FILEUSER;
    }

    public Map<String, User> getUsers() {
        return getSaveObject();
    }

    @Override
    public Map<String, User> defaultSaveObject() {
        return Collections.emptyMap();
    }
}
