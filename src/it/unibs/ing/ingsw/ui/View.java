package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.Config;
import it.unibs.ing.ingsw.category.CategoryView;

public class View {
    private final LoginView loginView;
    private final CategoryView categoryView;

    public View(Config config) {
        loginView = new LoginView(config);
        categoryView = new CategoryView();
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() {
        loginView.execute();
        categoryView.execute();
    }
}
