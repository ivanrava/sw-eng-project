package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.ingsw.Config;
import it.unibs.ing.ingsw.ConfigUsers;
import it.unibs.ing.ingsw.auth.LoginController;

import java.io.IOException;

public class LoginView {
    private final LoginController loginController;

    public LoginView(Config config, ConfigUsers confUsers) {
        loginController = new LoginController(config, confUsers);
    }

    /**
     * Esegue l'UI generale di login
     */
    public void execute() throws IOException {
        if (!loginController.existsDefaultCredentials()) {
            startSettingDefaultCredentials();
        }
        startLogin();
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
    private void startLogin() throws IOException {
        String username, password;
        if(!loginController.existsUsersCredentials()){
            startRegister();
        }
        else{
            do {
                username = InputDati.leggiStringaNonVuota("Inserisci lo username: ");
                password = InputDati.leggiStringaNonVuota("Inserisci la password: ");
                if (loginController.checkDefaultCredentials(username, password)) {
                    startRegister();
                } else if (!loginController.login(username, password)) {
                    System.out.println("Credenziali non corrette! :(");
                }
            } while (!loginController.login(username, password));
            System.out.printf("Sei dentro, %s%n", username);
        }
    }

    /**
     * Esegue l'UI di registrazione (per un nuovo utente)
     */
    private void startRegister() throws IOException {
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
            if (loginController.checkDefaultCredentials(username, password)) {
                System.out.println("Non puoi usare le credenziali di default :(");
            }
        } while (loginController.checkDefaultCredentials(username, password));
        loginController.register(username, password);

    }
}