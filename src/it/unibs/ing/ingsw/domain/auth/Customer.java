package it.unibs.ing.ingsw.domain.auth;

public class Customer extends User {
    public Customer(String username, String password) {
        super(username, password);
    }

    @Override
    public boolean isAdmin() { return false; }
}
