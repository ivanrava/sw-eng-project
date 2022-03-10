package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.Config;
import it.unibs.ing.ingsw.io.SaveUsers;
import it.unibs.ing.ingsw.category.CategoryView;

import java.io.IOException;

public class View {
    private final LoginView loginView;
    private final CategoryView categoryView;

    public View(Config config, SaveUsers confUsers) {
        loginView = new LoginView(config, confUsers);
        categoryView = new CategoryView();
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() throws IOException {
        loginView.execute();
        categoryView.execute();
    }
}
