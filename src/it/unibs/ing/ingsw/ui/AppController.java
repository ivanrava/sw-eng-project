package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.article.ExchangeController;
import it.unibs.ing.ingsw.auth.LoginMVController;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.io.DataContainer;

import java.time.Clock;

public class AppController {
    private final LoginMVController loginMVController;
    private final CustomerMVController customerMVController;
    private final ConfiguratorMVController configuratorMVController;
    private final ExchangeController exchangeController;

    public AppController(DataContainer saves, InputProvider inputProvider, Clock clock) {
        loginMVController = new LoginMVController(saves, inputProvider);
        customerMVController = new CustomerMVController(saves, inputProvider, clock);
        configuratorMVController = new ConfiguratorMVController(saves, inputProvider);
        exchangeController = new ExchangeController(saves, clock);
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() {
        // Controlla scambi scaduti
        exchangeController.deleteExpiredExchanges();

        do {
            loginMVController.execute();
            if (loginMVController.isLoggedIn()) {
                User loggedUser = loginMVController.getLoggedUser();
                if (loggedUser.isAdmin())
                    configuratorMVController.execute(loggedUser);
                else
                    customerMVController.execute(loggedUser);
                loginMVController.logout();
            } else if (loginMVController.wantsToExit())
                System.out.println("Uscita dal sistema");
        } while (!loginMVController.wantsToExit());
    }
}
