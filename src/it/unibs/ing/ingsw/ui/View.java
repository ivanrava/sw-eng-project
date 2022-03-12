package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.io.Saves;

import java.io.IOException;

public class View {
    private final LoginView loginView;
    private final CategoryView categoryView;

    public View(Saves saves) {
        loginView = new LoginView(saves);
        categoryView = new CategoryView(saves);
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() throws IOException {
        loginView.execute();
        categoryView.execute();
    }
}
