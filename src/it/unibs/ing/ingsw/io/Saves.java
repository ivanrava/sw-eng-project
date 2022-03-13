package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.auth.User;

import java.io.IOException;
import java.util.Map;

public class Saves {
    private final SaveConfig saveConfig;
    private final SaveUsers saveUsers;
    private final SaveHierarchies saveHierarchies;
   // private final SaveFConfiguration saveFConfiguration;

    public Saves() throws IOException, ClassNotFoundException {
        saveConfig = SaveConfig.load();
        saveUsers = SaveUsers.loadUsers();
        saveHierarchies= SaveHierarchies.loadHierarchies();
        //saveFConfiguration = SaveFConfiguration.loadFConfiguration();
    }

    public void save() throws IOException {
        saveConfig.save();
        saveUsers.saveUsers();
        saveHierarchies.saveHierarchies();
       // saveFConfiguration.saveFConfiguration();
    }

    //public SaveFConfiguration getSaveFConfiguration() { return saveFConfiguration; }

    public SaveHierarchies getSaveHierarchies() {
        return saveHierarchies;
    }

    public Map<String, User> getUsers() {
        return saveUsers.getUsers();
    }

    public String getDefaultUsername() {
        return saveConfig.getUsername();
    }

    public String getDefaultPassword() {
        return saveConfig.getPassword();
    }

    public void setUsername(String username) {
        saveConfig.setUsername(username);
    }

    public void setPassword(String password) {
        saveConfig.setPassword(password);
    }

    public boolean existsConfiguration() {
        return SaveConfig.exists();
    }

    // public boolean existsFileConfiguration {
    // return SaveFConfig.exists();}
}
