package it.unibs.ing.ingsw.auth;

import it.unibs.ing.ingsw.io.DataContainer;

import java.util.Map;

public class UserController {
    public static final String ASSERTION_REGISTER_DEFAULT_CREDENTIALS = "Si sta registrando un utente con credenziali di default!";
    public static final String ASSERTION_USERNAME_DUPLICATED = "Lo username non è univoco!";
    public static final String ASSERTION_USER_NONEXISTANT = "L'utente non esiste";
    private final Map<String, User> users;
    private final DataContainer saves;

    public UserController(DataContainer saves) {
        this.saves = saves;
        users = saves.getUsers();
    }

    /**
     * Controlla se lo username passato è già in uso da parte di un altro utente del sistema
     * @param username Username da controllare
     * @return 'true' se è già in uso, 'false' altrimenti
     */
    public boolean existsUsername(String username) {
        return users.containsKey(username) || saves.getDefaultUsername().equals(username);
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
     * @param username Username con cui fare la ricerca
     * @return L'utente ricavato dallo username
     */
    public User getUserByUsername(String username) {
        assert users.get(username) != null : ASSERTION_USER_NONEXISTANT;
        return users.get(username);
    }


    /**
     * Controlla se le credenziali passate corrispondono alle credenziali di default
     * @param username Username da controllare
     * @param password Password da controllare
     * @return 'true' se corrispondono, 'false' altrimenti
     */
    public boolean checkDefaultCredentials(String username, String password) {
        return username.equals(saves.getDefaultUsername()) && password.equals(saves.getDefaultPassword());
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
    public void register(String username, String password, boolean isAdmin) {
        assert !existsUsername(username) : ASSERTION_USERNAME_DUPLICATED;
        assert !checkDefaultCredentials(username, password) : ASSERTION_REGISTER_DEFAULT_CREDENTIALS;
        if (isAdmin)
            users.put(username, new Configurator(username, password));
        else
            users.put(username, new Customer(username, password));
    }

    /**
     * Imposta le credenziali di default dell'applicazione, usate per creare un nuovo utente
     * @param username Username di default
     * @param password Password di default
     */
    public void setDefaultCredentials(String username, String password) {
        saves.setDefaultCredentials(username, password);
    }

    /**
     * Controlla se esistono già delle credenziali di default
     * @return 'true' se esistono, 'false' altrimenti
     */
    public boolean existsDefaultCredentials() {
        return saves.existsDefaultCredentials();
    }
}
