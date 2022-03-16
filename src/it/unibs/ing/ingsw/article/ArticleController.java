package it.unibs.ing.ingsw.article;

import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.auth.UserController;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.category.CategoryController;
import it.unibs.ing.ingsw.category.Field;
import it.unibs.ing.ingsw.io.Saves;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleController {
    private static final String ASSERTION_DIFFERENT_FIELDS = "I campi della categoria e i campi passati non sono gli stessi!";
    public static final String ASSERTION_ARTICLE_UNEXISTANT = "Non esiste questo articolo!";
    private final Map<Integer, Article> articles;
    private final UserController userController;
    private final CategoryController categoryController;

    public ArticleController(Saves saves) {
        articles = saves.getArticles();
        userController = new UserController(saves);
        categoryController = new CategoryController(saves);
    }

    public List<Article> getArticlesForUser(String username) {
        return articles.values().stream()
                .filter(article -> article.getOwnerUsername().equals(username))
                .toList();
    }

    public List<Article> getArticlesForCategory(String categoryRootName, String categoryLeafName) {
        Category category = categoryController.searchTree(categoryRootName, categoryLeafName);
        return articles.values().stream()
                .filter(article -> article.getCategory().equals(category))
                .filter(article -> article.getState().equals(ArticleState.OFFERTA_APERTA))
                .toList();
    }

    public void addArticle(String username, String rootCategoryName, String leafCategoryName, ArticleState articleState, Map<String, String> fieldValues) {
        User user = userController.getUserByUsername(username);

        Category leafCategory = categoryController.searchTree(rootCategoryName, leafCategoryName);
        assert fieldValues.keySet().equals(leafCategory.getFields()) : ASSERTION_DIFFERENT_FIELDS;
        Map<String, Field> fields = new HashMap<>(leafCategory.getFields());
        for (String fieldName: fields.keySet()) {
            fields.get(fieldName).setValue(fieldValues.get(fieldName));
        }
        articles.put(articles.size()+1, new Article(articles.size()+1, user, leafCategory, articleState, fields));
    }

    public boolean exists(int id) {
        return articles.containsKey(id);
    }

    public String getArticle(int id) {
        assert exists(id) : ASSERTION_ARTICLE_UNEXISTANT;
        return articles.get(id).toString();
    }

    public void updateState(int id, ArticleState askArticleState) {
        assert exists(id) : ASSERTION_ARTICLE_UNEXISTANT;
        articles.get(id).setState(askArticleState);
    }
}
