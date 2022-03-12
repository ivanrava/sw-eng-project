package it.unibs.ing.ingsw.auth;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

public class LoginView {
    private final LoginController loginController;

    public LoginView(Saves saves) {
        loginController = new LoginController(saves);
    }

    /**
     * Esegue l'UI generale di login
     */
    public User execute() {
        if (!loginController.existsDefaultCredentials()) {
            startSettingDefaultCredentials();
        }

        MyMenu loginRegisterMenu = new MyMenu("Accesso", new String[] {
                "Registra nuovo utente",
                "Effettua accesso"
        });

        User loggedUser = null;//TODO tanto non é mai null
        int scelta;
        do {
            scelta = loginRegisterMenu.scegli();
            switch (scelta) {
                case 1 -> startRegister(false);
                case 2 -> {
                    loggedUser = startLogin();
                    scelta = 0;
                }
            }
        }while (scelta != 0);
        assert loggedUser != null:"";
        return loggedUser;
    }

    /**
     * Esegue l'UI di impostazione delle credenziali di default.
     *
     * Nella vita dell'applicazione questo metodo dovrebbe venire chiamato una volta sola:
     * al primo avvio senza configurazione.
     */
    private void startSettingDefaultCredentials() {
        String username, password;
        username = InputDati.leggiStringaNonVuota("Inserisci lo username di default: ");
        password = InputDati.leggiStringaNonVuota("Inserisci la password di default: ");
        loginController.setDefaultCredentials(username, password);
    }

    /**
     * Esegue l'UI di login
     */
    private User startLogin() {
        String username, password;

        //accesso utente
        do {
            username = InputDati.leggiStringaNonVuota("Inserisci lo username: ");
            password = InputDati.leggiStringaNonVuota("Inserisci la password: ");
            if (loginController.checkDefaultCredentials(username, password)) {
                startRegister(true);
            } else if (!loginController.login(username, password)) {
                System.out.println("Credenziali non corrette! :(");
            }
        } while (!loginController.login(username, password));
        System.out.printf("Sei dentro, %s%n", username);

        //admin?
        User utente = loginController.getUserByUsername(username);
        if (utente.isAdmin()){
            System.out.println("Admin");
        }else {
            System.out.println("Customer");
        }

        return utente;
    }

    /**
     * Esegue l'UI di registrazione (per un nuovo utente)
     */
    private void startRegister(boolean isAdmin) {
        String username, password;
        System.out.println("* Registrazione nuovo utente *");
        do {
            do {
                username = InputDati.leggiStringaNonVuota("Inserisci il tuo nuovo username: ");
                if (loginController.existsUsername(username)) {
                    System.out.println("Lo username esiste già :(");
                }
            } while (loginController.existsUsername(username));
            password = InputDati.leggiStringaNonVuota("Inserisci la tua nuova password: ");
        } while (loginController.checkDefaultCredentials(username, password));
        loginController.register(username, password, isAdmin);
    }
}