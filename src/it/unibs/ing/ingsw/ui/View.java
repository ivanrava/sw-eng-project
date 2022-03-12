package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.auth.LoginView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.io.Saves;

import java.io.IOException;

public class View {
    private final LoginView loginView;
    private final CategoryView categoryView;
    private final CustomerView customerView;

    public View(Saves saves) {
        loginView = new LoginView(saves);
        categoryView = new CategoryView(saves);
        customerView = new CustomerView(saves);
    }

    /**
     * Esegue l'UI generale dell'applicazione
     */
    public void execute() throws IOException {
        User utente = loginView.execute();

        if (utente.isAdmin()){
            //TODO generalizzare
            categoryView.execute();
        }else {
            customerView.execute();
        }


    }
}
