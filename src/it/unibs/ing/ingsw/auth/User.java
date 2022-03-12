package it.unibs.ing.ingsw.auth;

import java.io.Serializable;

public abstract class User implements Serializable {
    private final String username;
    private final String password;

    public User(String u, String pw) {
        username = u;
        password = pw;
    }

    public abstract boolean isAdmin();

    /**
     * Controlla se le credenziali corrispondono
     * @param u Username
     * @param pw Password
     * @return 'true' se corrispondono, 'false' altrimenti
     */
    public boolean checkCredentials(String u, String pw) {
        return u.equals(username) && pw.equals(password);
    }
}
