package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.article.ExchangeController;
import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.exceptions.ErrorDialog;
import it.unibs.ing.ingsw.exceptions.NoUserException;
import it.unibs.ing.ingsw.exceptions.WrongCredentialsException;
import it.unibs.ing.ingsw.io.Saves;

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
    public void execute() {
        // Controlla scambi scaduti
        exchangeController.deleteExpiredExchanges();
        while (true) {
            try {
                User user = loginView.execute();
                if (user.isAdmin()) {
                    configuratorView.execute(user);
                } else {
                    customerView.execute(user);
                }
            } catch (WrongCredentialsException e) {
                ErrorDialog.print(e);
            } catch (NoUserException e) {
                ErrorDialog.print(e);
                System.out.println("Uscita dal sistema");
                break;
            }
        }
    }
}
