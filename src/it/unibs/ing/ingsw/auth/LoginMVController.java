package it.unibs.ing.ingsw.auth;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.io.DataContainer;

public class LoginMVController {
    public static final String ERROR_CREDENTIALS = "Credenziali non corrette! :(";
    public static final String REGISTER_NEW_USER = "* Registrazione nuovo utente *";
    public static final String LOGIN_BANNER = "Sei dentro, %s%n";
    private final UserController userController;
    private final LoginView loginView;
    private User selectedUser;
    private boolean wantsToExit = false;

    public LoginMVController(DataContainer saves, InputProvider inputProvider) {
        userController = new UserController(saves);
        loginView = new LoginView(inputProvider);
    }

    /**
     * Esegue l'UI generale di login
     */
    public void execute() {
        if (!userController.existsDefaultCredentials()) {
            userController.setDefaultCredentials(
                    loginView.askDefaultUsername(),
                    loginView.askDefaultPassword()
            );
        }

        switch (loginView.scegli()) {
            case 1 -> startRegister(false);
            case 2 -> tryLogin(loginView.askLoginUsername(), loginView.askLoginPassword());
            case 0 -> wantsToExit = true;
        }
    }

    /**
     * Esegue l'UI di login
     */
    private void tryLogin(String username, String password) {
        if (userController.checkDefaultCredentials(username, password)) {
            startRegister(true);
        } else if (!userController.login(username, password)) {
            loginView.message(ERROR_CREDENTIALS);
        } else {
            selectedUser = userController.getUserByUsername(username);
            loginView.message(String.format(LOGIN_BANNER, username));
        }
    }

    public void logout() {
        selectedUser = null;
    }

    /**
     * Esegue l'UI di registrazione (per un nuovo utente)
     */
    private void startRegister(boolean isAdmin) {
        loginView.message(REGISTER_NEW_USER);
        String username = loginView.askRegisterUsername(userController);
        String password = loginView.askRegisterPassword();
        userController.register(username, password, isAdmin);
    }

    public boolean isLoggedIn() {
        return selectedUser != null;
    }

    public User getLoggedUser() {
        return selectedUser;
    }

    public boolean wantsToExit() {
        return wantsToExit;
    }
}