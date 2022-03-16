package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Saves {
    private final SaveConfig saveConfig;
    private final SaveUsers saveUsers;
    private final SaveHierarchies saveHierarchies;
    private final SaveArticles saveArticles;

    public Saves() throws IOException, ClassNotFoundException {
        saveConfig = SaveConfig.loadConfig();
        saveUsers = SaveUsers.loadUsers();
        saveHierarchies = SaveHierarchies.loadHierarchies();
        saveArticles = SaveArticles.loadArticles();
    }

    public void save() throws IOException {
        saveConfig.save();
        saveUsers.saveUsers();
        saveHierarchies.saveHierarchies();
    }

    public Config getConfig (){ return saveConfig.getConfig(); }

    public Map<String, Category> getSaveHierarchies() {
        return saveHierarchies.getHierarchies();
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

    public void setDefaultCredentials(String username, String password) {
        saveConfig.setImmutableValues(username, password);
    }

    public boolean existsConfiguration() {
        return SaveConfig.exists();
    }

    public boolean isConfigured() {
        return saveConfig.isConfigured();
    }

    public Map<Integer, Article> getArticles() {
        return saveArticles.getArticles();
    }
}
