package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.ingsw.auth.LoginController;

public class LoginView {
    private final LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
    }

    public void startLogin() {
        String username, password;
        do {
            username = InputDati.leggiStringaNonVuota("Inserisci lo username: ");
            password = InputDati.leggiStringaNonVuota("Inserisci la password: ");
            if (!loginController.login(username, password)) {
                System.out.println("Credenziali non corrette! :(");
            }
        } while (!loginController.login(username, password));
        System.out.printf("Sei dentro, %s%n", username);
    }
}
