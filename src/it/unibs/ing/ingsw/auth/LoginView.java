package it.unibs.ing.ingsw.auth;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

public class LoginView {
    public static final String MENU_TITLE = "Accesso";
    public static final String[] VOCI = {
            "Registra nuovo utente",
            "Effettua accesso"
    };
    public static final String INSERT_DEFAULT_USERNAME = "Inserisci lo username di default: ";
    public static final String INSERT_DEFAULT_PASSWORD = "Inserisci la password di default: ";
    public static final String INSERT_USERNAME = "Inserisci lo username: ";
    public static final String INSERT_PASSWORD = "Inserisci la password: ";
    public static final String ERROR_CREDENTIALS = "Credenziali non corrette! :(";
    public static final String REGISTER_NEW_USER = "* Registrazione nuovo utente *";
    public static final String INSERT_NEW_USERNAME = "Inserisci il tuo nuovo username: ";
    public static final String ERROR_USERNAME_DUPLICATED = "Lo username esiste già :(";
    public static final String INSERT_NEW_PASSWORD = "Inserisci la tua nuova password: ";
    public static final String LOGIN_BANNER = "Sei dentro, %s%n";
    public static final String ERROR_NO_LOGIN = "Non é stato effettuato l'accesso";
    private final UserController userController;

    public LoginView(Saves saves) {
        userController = new UserController(saves);
    }

    /**
     * Esegue l'UI generale di login
     */
    public User execute() {
        if (!userController.existsDefaultCredentials()) {
            startSettingDefaultCredentials();
        }

        MyMenu loginRegisterMenu = new MyMenu(MENU_TITLE, VOCI);

        int scelta;
        do {
            scelta = loginRegisterMenu.scegli();
            switch (scelta) {
                case 1 -> startRegister(false);
                case 2 -> {
                    return startLogin();
                }
            }
        } while (scelta != 0);
        throw new RuntimeException(ERROR_NO_LOGIN);
    }

    /**
     * Esegue l'UI di impostazione delle credenziali di default.
     *
     * Nella vita dell'applicazione questo metodo dovrebbe venire chiamato una volta sola:
     * al primo avvio senza configurazione.
     */
    private void startSettingDefaultCredentials() {
        String username, password;
        username = InputDati.leggiStringaNonVuota(INSERT_DEFAULT_USERNAME);
        password = InputDati.leggiStringaNonVuota(INSERT_DEFAULT_PASSWORD);
        userController.setDefaultCredentials(username, password);
    }

    /**
     * Esegue l'UI di login
     */
    private User startLogin() {
        String username, password;
        do {
            username = InputDati.leggiStringaNonVuota(INSERT_USERNAME);
            password = InputDati.leggiStringaNonVuota(INSERT_PASSWORD);
            if (userController.checkDefaultCredentials(username, password)) {
                startRegister(true);
            } else if (!userController.login(username, password)) {
                throw new IllegalArgumentException(ERROR_CREDENTIALS);
            }
        } while (!userController.login(username, password));
        System.out.printf(LOGIN_BANNER, username);

        return userController.getUserByUsername(username);
    }

    /**
     * Esegue l'UI di registrazione (per un nuovo utente)
     */
    private void startRegister(boolean isAdmin) {
        String username, password;
        System.out.println(REGISTER_NEW_USER);
        do {
            do {
                username = InputDati.leggiStringaNonVuota(INSERT_NEW_USERNAME);
                if (userController.existsUsername(username)) {
                    System.out.println(ERROR_USERNAME_DUPLICATED);
                }
            } while (userController.existsUsername(username));
            password = InputDati.leggiStringaNonVuota(INSERT_NEW_PASSWORD);
        } while (userController.checkDefaultCredentials(username, password));
        userController.register(username, password, isAdmin);
    }
}