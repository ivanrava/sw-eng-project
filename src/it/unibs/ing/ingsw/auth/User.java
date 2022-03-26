package it.unibs.ing.ingsw.auth;

import java.io.Serializable;

public abstract class User implements Serializable {
    private final String username;
    private final String password;

    /**
     * Costruttore parametrizzato
     * @param u Username dell'utente
     * @param pw Password dell'utente
     */
    public User(String u, String pw) {
        username = u;
        password = pw;
    }

    public String getUsername() {
        return username;
    }

    /**
     * @return 'true' se l'utente ha poteri amministrativi, 'false' se non privilegiato
     */
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

    //TODO: pensiamo su
    public boolean equals(User user) {
        return user.getUsername().equals(username);
    }
}
