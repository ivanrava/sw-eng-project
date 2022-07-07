package it.unibs.ing.ingsw.ui.views;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.domain.auth.UserController;

public class LoginView extends AbstractView {
    public static final String INSERT_DEFAULT_USERNAME = "Inserisci lo username di default: ";
    public static final String INSERT_DEFAULT_PASSWORD = "Inserisci la password di default: ";
    public static final String INSERT_USERNAME = "Inserisci lo username: ";
    public static final String INSERT_PASSWORD = "Inserisci la password: ";
    public static final String INSERT_NEW_USERNAME = "Inserisci il tuo nuovo username: ";
    public static final String ERROR_USERNAME_DUPLICATED = "Lo username esiste già :(";
    public static final String INSERT_NEW_PASSWORD = "Inserisci la tua nuova password: ";
    public static final String MENU_TITLE = "Accesso";
    public static final String[] VOCI = {
            "Registra nuovo utente",
            "Effettua accesso"
    };

    public LoginView(InputProvider inputProvider) {
        super(inputProvider);
    }

    public String askDefaultUsername() {
        return inputProvider.leggiStringaNonVuota(INSERT_DEFAULT_USERNAME);
    }

    public String askDefaultPassword() {
        return inputProvider.leggiStringaNonVuota(INSERT_DEFAULT_PASSWORD);
    }

    public String askLoginUsername() {
        return inputProvider.leggiStringaNonVuota(INSERT_USERNAME);
    }

    public String askLoginPassword() {
        return inputProvider.leggiStringaNonVuota(INSERT_PASSWORD);
    }

    public String askRegisterUsername(UserController userController) {
        String username;
        do {
            username = inputProvider.leggiStringaNonVuota(INSERT_NEW_USERNAME);
            if (userController.existsUsername(username)) {
                System.out.println(ERROR_USERNAME_DUPLICATED);
            }
        } while (userController.existsUsername(username));
        return username;
    }

    public String askRegisterPassword() {
        return inputProvider.leggiStringaNonVuota(INSERT_NEW_PASSWORD);
    }

    public int scegli() {
        MyMenu loginRegisterMenu = new MyMenu(MENU_TITLE, VOCI, inputProvider);
        return loginRegisterMenu.scegli();
    }
}
