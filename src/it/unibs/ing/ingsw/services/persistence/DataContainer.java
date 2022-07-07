package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.domain.business.Article;
import it.unibs.ing.ingsw.domain.business.Exchange;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.business.Category;
import it.unibs.ing.ingsw.domain.config.Config;

import java.util.List;
import java.util.Map;

public interface DataContainer extends Saveable{
    Config getConfig();

    void setConfig(Config config);

    Map<String, Category> getHierarchies();

    Map<String, User> getUsers();

    Map<Integer, Article> getArticles();

    List<Exchange> getExchanges();

    String getDefaultUsername();

    String getDefaultPassword();

    void setDefaultCredentials(String username, String password);

    boolean existsConfiguration();

    boolean existsDefaultCredentials();
}
