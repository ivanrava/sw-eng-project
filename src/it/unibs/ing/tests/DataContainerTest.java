package it.unibs.ing.tests;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.Exchange;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.TimeInterval;
import it.unibs.ing.ingsw.exceptions.SaveException;
import it.unibs.ing.ingsw.io.DataContainer;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class DataContainerTest implements DataContainer {
    private String defaultUsername;
    private String defaultPassword;

    @Override
    public Config getConfig() {
        return new Config(
                "square",
                Set.of("place1", "place2"),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                Set.of(new TimeInterval(LocalTime.of(0, 0), LocalTime.of(1, 0))),
                5
        );
    }

    @Override
    public void setConfig(Config config) {

    }

    @Override
    public Map<String, Category> getHierarchies() {
        return new HashMap<>();
    }

    @Override
    public Map<String, User> getUsers() {
        return new HashMap<>();
    }

    @Override
    public Map<Integer, Article> getArticles() {
        return new HashMap<>();
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
        return false;
    }

    @Override
    public boolean existsDefaultCredentials() {
        return defaultUsername != null && defaultPassword != null;
    }

    @Override
    public void save() {

    }
}
