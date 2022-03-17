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

    /**
     * costruttore in cui vengono caricati tutti i file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Saves() throws IOException, ClassNotFoundException {
        saveConfig = SaveConfig.loadConfig();
        saveUsers = SaveUsers.loadUsers();
        saveHierarchies = SaveHierarchies.loadHierarchies();
        saveArticles = SaveArticles.loadArticles();
    }

    /**
     * Metodo per salvataggio di tutti i file
     * @throws IOException
     */
    public void save() throws IOException {
        saveConfig.save();
        saveUsers.saveUsers();
        saveHierarchies.saveHierarchies();
        saveArticles.saveArticles();
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

    /**
     * @return 'true' se esiste file di configurazione, 'false' altrimenti
     */
    public boolean existsConfiguration() {
        return SaveConfig.exists();
    }

    /**
     * @return 'true' se esiste configurazione, 'false' altrimenti
     */
    public boolean isConfigured() {
        return saveConfig.isConfigured();
    }

    public Map<Integer, Article> getArticles() {
        return saveArticles.getArticles();
    }
}
