package it.unibs.ing.ingsw.auth;

import it.unibs.ing.ingsw.Config;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private final Map<String, User> users;
    // FIXME: accoppiamento tra Config e LoginController?
    private final Config config;

    public LoginController(Config config) {
        this.config = config;
        users = new HashMap<>();
    }

    /**
     * Controlla se lo username passato è già in uso da parte di un altro utente del sistema
     * @param username Username da controllare
     * @return 'true' se è già in uso, 'false' altrimenti
     */
    public boolean existsUsername(String username) {
        return users.containsKey(username);
    }

    /**
     * Controlla se l'utente può accedere al sistema
     * @param username Username da controllare
     * @param password Password da controllare
     * @return 'true' se può accedere, 'false' se non può accedere
     */
    public boolean login(String username, String password) {
        // Check if exists
        if (users.get(username) == null) {
            return false;
        }
        // Verifies credentials
        return users.get(username).checkCredentials(username, password);
    }

    /**
     * Controlla se le credenziali passate corrispondono alle credenziali di default
     * @param username Username da controllare
     * @param password Password da controllare
     * @return 'true' se corrispondono, 'false' altrimenti
     */
    public boolean checkDefaultCredentials(String username, String password) {
        return username.equals(config.getUsername()) && password.equals(config.getPassword());
    }

    /**
     * Precondizione: lo username non dev'essere già in uso
     * Precondizione: le credenziali passate devono essere diverse dalle credenziali di default
     *
     * Registra un nuovo utente nel sistema
     *
     * @param username Username del nuovo utente da aggiungere
     * @param password Password del nuovo utente da aggiungere
     */
    public void register(String username, String password) {
        assert !existsUsername(username) : "Lo username non è univoco!";
        assert checkDefaultCredentials(username, password) : "Si sta registrando un utente con credenziali di default!";
        users.put(username, new User(username, password));
    }

    /**
     * Imposta le credenziali di default dell'applicazione, usate per creare un nuovo utente
     * @param username Username di default
     * @param password Password di default
     */
    public void setDefaultCredentials(String username, String password) {
        config.setUsername(username);
        config.setPassword(password);
    }

    /**
     * Controlla se esistono già delle credenziali di default
     * @return 'true' se esistono, 'false' altrimenti
     */
    public boolean existsDefaultCredentials() {
        return Config.exists();
    }
}
