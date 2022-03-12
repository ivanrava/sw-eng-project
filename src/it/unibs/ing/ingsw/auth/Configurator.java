package it.unibs.ing.ingsw.auth;

public class Configurator extends User {
    public Configurator(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean isAdmin() {
        return true;
    }
}
