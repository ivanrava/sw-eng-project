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
    public final Map<Integer, Article> articles;
    private final UserController userController;
    private final CategoryController categoryController;

    /**
     * Costruttore parametrizzato
     * @param saves Salvataggi dell'applicazione
     */
    public ArticleController(Saves saves) {
        articles = saves.getArticles();
        userController = new UserController(saves);
        categoryController = new CategoryController(saves);
    }

    /**
     * Ritorna una lista di articoli appartenenti ad un certo utente
     * @param username Username dell'utente
     * @return Lista di articoli di quell'utente
     */
    public List<Article> getArticlesForUser(String username) {
        return articles.values().stream()
                .filter(article -> article.getOwnerUsername().equals(username))
                .toList();
    }

    public List<Article> getArticlesAvailableForUser(String username) {
        return articles.values().stream()
                .filter(article -> article.getOwnerUsername().equals(username))
                .filter(Article::isAvailable)
                .toList();
    }

    /**
     * @param categoryRootName Nome della categoria radice
     * @param categoryLeafName Nome della categoria foglia
     * @param isAdmin Indica se ricevere i risultati destinati agli utenti privilegiati oppure agli utenti semplici
     * @return Lista di articoli di una categoria foglia
     */
    public List<Article> getArticlesForCategory(String categoryRootName, String categoryLeafName, boolean isAdmin) {
        Category category = categoryController.searchTree(categoryRootName, categoryLeafName);
        return articles.values().stream()
                .filter(article -> article.getCategory().equals(category))
                .filter(article -> {
                    ArticleState state = article.getState();
                    if (isAdmin) {
                        return state.equals(ArticleState.OFFERTA_APERTA) ||
                                state.equals(ArticleState.OFFERTA_SCAMBIO) ||
                                state.equals(ArticleState.OFFERTA_CHIUSA);
                    } else {
                        return state.equals(ArticleState.OFFERTA_APERTA);
                    }
                })
                .toList();
    }

    /**
     * Aggiunge un articolo
     * @param username Username dell'utente che lo possiede
     * @param rootCategoryName Nome della categoria radice della categoria dell'articolo
     * @param leafCategoryName Nome della categoria foglia dell'articolo
     * @param articleState Stato dell'articolo
     * @param fieldValues Valori dei campi della categoria foglia
     */
    public void addArticle(String username, String rootCategoryName, String leafCategoryName, ArticleState articleState, Map<String, String> fieldValues) {
        User user = userController.getUserByUsername(username);
        Category leafCategory = categoryController.searchTree(rootCategoryName, leafCategoryName);
        assert fieldValues.keySet().equals(leafCategory.getFields()) : ASSERTION_DIFFERENT_FIELDS;
        Map<String, Field> fields = new HashMap<>(leafCategory.getFields());
        System.out.println(articles);

        for (String fieldName: fields.keySet()) {
            fields.get(fieldName).setValue(fieldValues.get(fieldName));
        }

        System.out.println(articles);
        articles.put(articles.size()+1, new Article(articles.size()+1, user, leafCategory, articleState, fields));
        System.out.println(articles);
    }

    /**
     * Controlla che esista un articolo con un certo id
     * @param id Identificatore numerico dell'articolo
     * @return 'true' se esiste, 'false' altrimenti
     */
    public boolean exists(int id) {
        return articles.containsKey(id);
    }

    /**
     * @param id Identificatore numerico dell'articolo
     * @return L'articolo con l'id passato
     */
    // FIXME: accoppiamento o no?
    public Article getArticle(int id) {
        assert exists(id) : ASSERTION_ARTICLE_UNEXISTANT;
        return articles.get(id);
    }

    /**
     * Aggiorna lo stato dell'articolo
     * @param id Identificatore numerico dell'articolo
     * @param newArticleState Nuovo stato dell'articolo
     */
    public void updateState(int id, ArticleState newArticleState) {
        assert exists(id) : ASSERTION_ARTICLE_UNEXISTANT;
        articles.get(id).setState(newArticleState);
    }

    /**
     * Ritorna gli articoli disponibili per il baratto
     * @param user Utente che richiede lo scambio
     * @param category Categoria in cui avviene lo scambio
     * @return Articoli disponibili per il baratto
     */
    public List<Article> getAvailableArticlesForExchange(User user, Category category) {
        assert category.isLeaf() : "La categoria selezionata per il baratto non è una foglia!";
        return articles.values().stream()
                .filter(article -> article.getCategory().equals(category) && article.isAvailable() && !article.getOwner().equals(user))
                .toList();
    }
}
