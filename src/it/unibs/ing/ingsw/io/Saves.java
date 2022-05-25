package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.Exchange;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.exceptions.SaveException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Saves {
    private static final String ERROR_LOAD = "Errore caricamento Saves";
    private static final String ERROR_SAVE = "Errore salvataggio Saves";
    private final SaveConfig saveConfig;
    private final SaveUsers saveUsers;
    private final SaveHierarchies saveHierarchies;
    private final SaveArticles saveArticles;
    private final SaveExchanges saveExchanges;

    /**
     * costruttore in cui vengono caricati tutti i file
     */
    public Saves() throws LoadSavesException {
        try {
            saveConfig = SaveConfig.loadConfig();
            saveUsers = SaveUsers.loadUsers();
            saveHierarchies = SaveHierarchies.loadHierarchies();
            saveArticles = SaveArticles.loadArticles();
            saveExchanges = SaveExchanges.loadExchanges();
        } catch (IOException | ClassNotFoundException e) { //FIXME: magari gestire exception in modo piu specifico nei vari Saves (forse troppo specifico ?!)
            throw new LoadSavesException(ERROR_LOAD);
        }

    }

    /**
     * Metodo per salvataggio di tutti i file
     */
    public void save() throws SaveException {
        try {
            saveConfig.save();
            saveUsers.saveUsers();
            saveHierarchies.saveHierarchies();
            saveArticles.saveArticles();
            saveExchanges.saveExchanges();
        } catch (IOException e) {
            throw new SaveException(ERROR_SAVE);
        }
    }

    public Config getConfig() {
        return saveConfig.getConfig();
    }

    public void setConfig(Config config) {
        saveConfig.setConfig(config);
    }

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

    /**
     * Imposta le credenziali di default
     *
     * @param username Username di default
     * @param password Password di default
     */
    public void setDefaultCredentials(String username, String password) {
        saveConfig.setImmutableValues(username, password);
    }

    /**
     * @return 'true' se i valori di default sono stati impostati (credenziali e valori immutabili della configurazione),
     * 'false' altrimenti
     */
    public boolean existsConfiguration() {
        return saveConfig.isConfiguredDefaultCredentials() && saveConfig.isConfiguredImmutableValues();
    }

    public Map<Integer, Article> getArticles() {
        return saveArticles.getArticles();
    }

    public List<Exchange> getExchanges() {
        return saveExchanges.getExchanges();
    }

    /**
     * @return 'true' se sono state configurate le credenziali, 'false' altrimenti
     */
    public boolean existsDefaultCredentials() {
        return saveConfig.isConfiguredDefaultCredentials();
    }
}
