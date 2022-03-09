package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.category.CategoryView;

public class View {
    private final LoginView loginView = new LoginView();
    private final CategoryView categoryView = new CategoryView();

    public void execute() {
        //loginView.startLogin();
        categoryView.execute();
    }
}
