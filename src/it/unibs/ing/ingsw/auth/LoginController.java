package it.unibs.ing.ingsw.auth;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private Map<String, User> users;
    
    public LoginController() {
        users = new HashMap<>();
        users.put("rizzo", new User("rizzo", "rizzofuori"));
    }
    
    public boolean login(String username, String password) {
        // Check if exists
        if (users.get(username) == null) {
            return false;
        }
        // Verifies credentials
        return users.get(username).check(username, password);
        /*
        */
    }
}
