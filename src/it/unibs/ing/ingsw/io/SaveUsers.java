package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.auth.User;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveUsers implements Serializable {
    public static final String CONFIG_SAVE_FILEUSER = "./usersRegister.dat";
    private final Map<String, User> users = new HashMap<>();

    public Map<String, User> getUsers() {
        return users;
    }

    public static SaveUsers loadUsers() throws IOException, ClassNotFoundException {
        File f = new File(CONFIG_SAVE_FILEUSER);
        if (f.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(f)))) {
                return (SaveUsers) inputStream.readObject();
            }
        }

        return new SaveUsers();
    }

    public void saveUsers() throws IOException {
        File f = new File(CONFIG_SAVE_FILEUSER);
            try (ObjectOutputStream outputStream = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(f)))) {
                outputStream.writeObject(this);
            }
    }
}
