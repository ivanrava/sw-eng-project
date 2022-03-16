package it.unibs.ing.ingsw.auth;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

import java.util.Optional;

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
    private final LoginController loginController;

    public LoginView(Saves saves) {
        loginController = new LoginController(saves);
    }

    /**
     * Esegue l'UI generale di login
     */
    public Optional<Boolean> execute() {
        if (!loginController.existsDefaultCredentials()) {
            startSettingDefaultCredentials();
        }

        MyMenu loginRegisterMenu = new MyMenu(MENU_TITLE, VOCI);

        int scelta;
        do {
            scelta = loginRegisterMenu.scegli();
            switch (scelta) {
                case 0 -> {
                    return Optional.empty();
                }
                case 1 -> startRegister(false);
                case 2 -> scelta = 0;
            }
        } while (scelta != 0);

        return Optional.of(startLogin().isAdmin());
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
        loginController.setDefaultCredentials(username, password);
    }

    /**
     * Esegue l'UI di login
     */
    private User startLogin() {
        String username, password;

        //accesso utente
        do {
            username = InputDati.leggiStringaNonVuota(INSERT_USERNAME);
            password = InputDati.leggiStringaNonVuota(INSERT_PASSWORD);
            if (loginController.checkDefaultCredentials(username, password)) {
                startRegister(true);
            } else if (!loginController.login(username, password)) {
                System.out.println(ERROR_CREDENTIALS);
            }
        } while (!loginController.login(username, password));
        System.out.printf("Sei dentro, %s%n", username);

        return loginController.getUserByUsername(username);
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
                if (loginController.existsUsername(username)) {
                    System.out.println(ERROR_USERNAME_DUPLICATED);
                }
            } while (loginController.existsUsername(username));
            password = InputDati.leggiStringaNonVuota(INSERT_NEW_PASSWORD);
        } while (loginController.checkDefaultCredentials(username, password));
        loginController.register(username, password, isAdmin);
    }
}