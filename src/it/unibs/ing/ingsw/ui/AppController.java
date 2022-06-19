package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.article.ExchangeController;
import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.io.DataContainer;

public class AppController {
    private final LoginView loginView;
    private final CustomerView customerView;
    private final ConfiguratorMVController configuratorMVController;
    private final ExchangeController exchangeController;

    public AppController(DataContainer saves) {
        loginView = new LoginView(saves);
        customerView = new CustomerView(saves);
        configuratorMVController = new ConfiguratorMVController(saves);
        exchangeController = new ExchangeController(saves);
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() {
        // Controlla scambi scaduti
        exchangeController.deleteExpiredExchanges();

        do {
            loginView.execute();
            if (loginView.isLoggedIn()) {
                User loggedUser = loginView.getLoggedUser();
                if (loggedUser.isAdmin())
                    configuratorMVController.execute(loggedUser);
                else
                    customerView.execute(loggedUser);
                loginView.logout();
            } else if (loginView.wantsToExit())
                System.out.println("Uscita dal sistema");
        } while (!loginView.wantsToExit());
    }
}
