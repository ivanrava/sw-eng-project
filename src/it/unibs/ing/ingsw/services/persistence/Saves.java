package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.domain.business.Article;
import it.unibs.ing.ingsw.domain.business.Exchange;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.business.Category;
import it.unibs.ing.ingsw.domain.config.Config;
import it.unibs.ing.ingsw.services.persistence.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.services.persistence.exceptions.SaveException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Saves implements DataContainer {
    private static final String ERROR_LOAD = "Errore caricamento Saves";
    public static final String SAVES_CONFIG = "saves.config";
    public static final String SAVES_USERS = "saves.users";
    public static final String SAVES_CATEGORIES = "saves.categories";
    public static final String SAVES_ARTICLES = "saves.articles";
    public static final String SAVES_EXCHANGES = "saves.exchanges";
    private final SaveConfig saveConfig;
    private final SaveUsers saveUsers;
    private final SaveCategories saveCategories;
    private final SaveArticles saveArticles;
    private final SaveExchanges saveExchanges;

    /**
     * costruttore in cui vengono caricati tutti i file
     */
    public Saves() throws LoadSavesException {
        try {
            saveConfig = new SaveConfig(System.getProperty(SAVES_CONFIG));
            saveUsers = new SaveUsers(System.getProperty(SAVES_USERS));
            saveCategories = new SaveCategories(System.getProperty(SAVES_CATEGORIES));
            saveArticles = new SaveArticles(System.getProperty(SAVES_ARTICLES));
            saveExchanges = new SaveExchanges(System.getProperty(SAVES_EXCHANGES));
        } catch (IOException |
                 ClassNotFoundException e) { //FIXME: magari gestire exception in modo piu specifico nei vari Saves (forse troppo specifico ?!)
            throw new LoadSavesException(ERROR_LOAD);
        }
    }


    /**
     * Metodo per salvataggio di tutti i file
     */
    public void save() throws SaveException {
        saveConfig.save();
        saveUsers.save();
        saveCategories.save();
        saveArticles.save();
        saveExchanges.save();
    }

    @Override
    public Config getConfig() {
        return saveConfig.getConfig();
    }

    @Override
    public void setConfig(Config config) {
        saveConfig.setConfig(config);
    }

    @Override
    public Map<String, Category> getHierarchies() {
        return saveCategories.getHierarchies();
    }

    @Override
    public Map<String, User> getUsers() {
        return saveUsers.getUsers();
    }

    @Override
    public Map<Integer, Article> getArticles() {
        return saveArticles.getArticles();
    }

    @Override
    public List<Exchange> getExchanges() {
        return saveExchanges.getExchanges();
    }

    @Override
    public String getDefaultUsername() {
        return saveConfig.getConfig().getUsername();
    }

    @Override
    public String getDefaultPassword() {
        return saveConfig.getConfig().getPassword();
    }

    /**
     * Imposta le credenziali di default
     *
     * @param username Username di default
     * @param password Password di default
     */
    @Override
    public void setDefaultCredentials(String username, String password) {
        saveConfig.getConfig().setDefaultCredentials(username, password);
    }

    /**
     * @return 'true' se i valori di default sono stati impostati (credenziali e valori immutabili della configurazione),
     * 'false' altrimenti
     */
    @Override
    public boolean existsConfiguration() {
        return saveConfig.getConfig().isConfiguredDefaultCredentials() && saveConfig.isConfiguredImmutableValues();
    }


    /**
     * @return 'true' se sono state configurate le credenziali, 'false' altrimenti
     */
    @Override
    public boolean existsDefaultCredentials() {
        return saveConfig.getConfig().isConfiguredDefaultCredentials();
    }
}
