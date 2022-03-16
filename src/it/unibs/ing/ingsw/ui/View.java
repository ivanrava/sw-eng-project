package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.io.Saves;

import java.io.IOException;
import java.util.Optional;

public class View {
    private final LoginView loginView;
    private final CustomerView customerView;
    private final ConfiguratorView configuratorView;

    public View(Saves saves) {
        loginView = new LoginView(saves);
        customerView = new CustomerView(saves);
        configuratorView = new ConfiguratorView(saves);
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() throws IOException {
        do {
            Optional<Boolean> isAdmin = loginView.execute();

            if (isAdmin.isEmpty()) {
                break;
            }

            if (isAdmin.get()) {
                configuratorView.execute();
            } else {
                customerView.execute();
            }
        } while (true);
    }
}
