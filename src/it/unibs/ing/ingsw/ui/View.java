package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.io.Saves;

import java.io.IOException;

public class View {
    private final LoginView loginView;
    private final CustomerView customerView;
    private final ConfiguratorView configuratorView;

    public View(Saves saves, Config configurazione) {
        loginView = new LoginView(saves);
        customerView = new CustomerView(saves, configurazione);
        configuratorView = new ConfiguratorView(saves, configurazione);
    }


    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() throws IOException {
        do {
            boolean isAdmin = loginView.execute();

            if (isAdmin) {
                configuratorView.execute();
            } else {
                customerView.execute();
            }
        } while (true);
    }
}
