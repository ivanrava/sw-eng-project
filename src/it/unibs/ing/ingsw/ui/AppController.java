package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.article.ExchangeController;
import it.unibs.ing.ingsw.auth.LoginMVController;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.io.DataContainer;

public class AppController {
    private final LoginMVController loginView;
    private final CustomerMVController customerMVController;
    private final ConfiguratorMVController configuratorMVController;
    private final ExchangeController exchangeController;

    public AppController(DataContainer saves, InputProvider inputProvider) {
        loginView = new LoginMVController(saves, inputProvider);
        customerMVController = new CustomerMVController(saves, inputProvider);
        configuratorMVController = new ConfiguratorMVController(saves, inputProvider);
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
                    customerMVController.execute(loggedUser);
                loginView.logout();
            } else if (loginView.wantsToExit())
                System.out.println("Uscita dal sistema");
        } while (!loginView.wantsToExit());
    }
}
