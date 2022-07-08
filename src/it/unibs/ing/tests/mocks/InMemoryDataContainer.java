package it.unibs.ing.tests.mocks;

import it.unibs.ing.ingsw.domain.business.Article;
import it.unibs.ing.ingsw.domain.business.Exchange;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.business.Category;
import it.unibs.ing.ingsw.domain.config.Config;
import it.unibs.ing.ingsw.services.persistence.DataContainer;

import java.util.*;

public class InMemoryDataContainer implements DataContainer {
    private String defaultUsername;
    private String defaultPassword;
    private Map<String, Category> hierarchies = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
    private Map<Integer, Article> articles = new HashMap<>();
    private final Config config;

    public InMemoryDataContainer(Config defaultConfig) {
        config = defaultConfig;
    }

    public InMemoryDataContainer() {
        config = new Config(new HashSet<>(), new TreeSet<>(), new TreeSet<>(), 1);
    }

    @Override
    public Config getConfig() {
        return config;
    }

    @Override
    public void setConfig(Config config) {
        config.setDays(config.getDays());
        config.setPlaces(config.getPlaces());
        config.setTimeIntervals(config.getTimeIntervals());
        config.setDeadline(config.getDeadline());
    }

    @Override
    public Map<String, Category> getHierarchies() {
        return hierarchies;
    }

    public void setHierarchies(Map<String, Category> hierarchies) {
        this.hierarchies = hierarchies;
    }

    @Override
    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }

    @Override
    public Map<Integer, Article> getArticles() {
        return articles;
    }

    public void setArticles(Map<Integer, Article> articles) {
        this.articles = articles;
    }

    @Override
    public List<Exchange> getExchanges() {
        return new ArrayList<>();
    }

    @Override
    public String getDefaultUsername() {
        return defaultUsername;
    }

    @Override
    public String getDefaultPassword() {
        return defaultPassword;
    }

    @Override
    public void setDefaultCredentials(String username, String password) {
        defaultUsername = username;
        defaultPassword = password;
    }

    @Override
    public boolean existsConfiguration() {
        return config.isConfiguredImmutableValues();
    }

    @Override
    public boolean existsDefaultCredentials() {
        return defaultUsername != null && defaultPassword != null;
    }

    @Override
    public void save() {

    }


}
