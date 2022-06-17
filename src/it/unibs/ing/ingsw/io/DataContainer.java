package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.Exchange;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;

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
