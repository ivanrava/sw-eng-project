package it.unibs.ing.ingsw.auth;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.ui.AbstractView;

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

    public String askDefaultUsername() {
        return InputDati.leggiStringaNonVuota(INSERT_DEFAULT_USERNAME);
    }

    public String askDefaultPassword() {
        return InputDati.leggiStringaNonVuota(INSERT_DEFAULT_PASSWORD);
    }

    public String askLoginUsername() {
        return InputDati.leggiStringaNonVuota(INSERT_USERNAME);
    }

    public String askLoginPassword() {
        return InputDati.leggiStringaNonVuota(INSERT_PASSWORD);
    }

    public String askRegisterUsername(UserController userController) {
        String username;
        do {
            username = InputDati.leggiStringaNonVuota(INSERT_NEW_USERNAME);
            if (userController.existsUsername(username)) {
                System.out.println(ERROR_USERNAME_DUPLICATED);
            }
        } while (userController.existsUsername(username));
        return username;
    }

    public String askRegisterPassword() {
        return InputDati.leggiStringaNonVuota(INSERT_NEW_PASSWORD);
    }

    public int scegli() {
        MyMenu loginRegisterMenu = new MyMenu(MENU_TITLE, VOCI);
        return loginRegisterMenu.scegli();
    }
}
