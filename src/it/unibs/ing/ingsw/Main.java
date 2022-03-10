package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.ui.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Config config = Config.load();
            SaveUsers configUsers = SaveUsers.loadUsers();
            View view = new View(config, configUsers);
            view.execute();
            config.save();
            configUsers.saveUsers();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("La configurazione è presente, ma non riesco a caricarla!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
