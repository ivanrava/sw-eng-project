package it.unibs.ing.ingsw.auth;

public class User {
    private final String username;
    private final String password;

    public User(String u, String pw) {
        username = u;
        password = pw;
    }

    public boolean check(String u, String pw) {
        return u.equals(username) && pw.equals(password);
    }
}
