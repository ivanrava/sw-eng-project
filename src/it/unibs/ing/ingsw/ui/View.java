package it.unibs.ing.ingsw.ui;

public class View {
    private final LoginView loginView = new LoginView();

    public void execute() {
        loginView.startLogin();
    }
}
