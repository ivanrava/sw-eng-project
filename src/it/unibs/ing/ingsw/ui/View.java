package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.article.ExchangeController;
import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.io.Saves;

import java.io.IOException;
import java.util.Optional;

public class View {
    private final LoginView loginView;
    private final CustomerView customerView;
    private final ConfiguratorView configuratorView;
    private final ExchangeController exchangeController;

    public View(Saves saves) {
        loginView = new LoginView(saves);
        customerView = new CustomerView(saves);
        configuratorView = new ConfiguratorView(saves);
        exchangeController = new ExchangeController(saves);
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() throws IOException {
        //controlla scambi scaduti
        exchangeController.deleteExpiredExchanges();

        do {
            Optional<User> user = loginView.execute();

            if (user.isEmpty()) {
                break;
            }

            if (user.get().isAdmin()) {
                configuratorView.execute(user.get());
            } else {
                customerView.execute(user.get());
            }
        } while (true);
    }
}
