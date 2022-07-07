package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.business.Article;
import it.unibs.ing.ingsw.domain.business.ArticleController;
import it.unibs.ing.ingsw.domain.business.ArticleState;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.ui.views.AbstractView;
import it.unibs.ing.ingsw.ui.views.ArticleView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ArticleMVController extends AbstractMVController {
    public static final String ASSERTION_CONFIGURATOR_ADD_ARTICLE = "Un configuratore non dovrebbe poter aggiungere articoli!";
    public static final String PRINT_USER_ARTICLES = "Visualizza articoli/offerte dell'utente";
    public static final String PRINT_CATEGORY_ARTICLES = "Visualizza articoli/offerte di una categoria";
    public static final String ADD_ARTICLE = "Aggiungi un articolo/offerta";
    public static final String MODIFY_ARTICLE_STATE = "Modifica stato di un'offerta";
    public static final String ERROR_NO_UPDATABLE_ARTICLES = "Non hai articoli modificabili";
    private final ArticleController articleController;
    private final CategoryMVController categoryMVController;
    private final ArticleView articleView;

    /**
     * Costruttore principale
     *
     * @param saves Istanza dei salvataggi dell'applicazione
     */
    public ArticleMVController(DataContainer saves, InputProvider inputProvider) {
        articleController = new ArticleController(saves);
        categoryMVController = new CategoryMVController(saves, inputProvider);
        articleView = new ArticleView(inputProvider);
    }

    @Override
    protected void beforeExecute() {
    }

    @Override
    protected LinkedHashMap<String, Runnable> getMenuOptions(User user) {
        LinkedHashMap<String, Runnable> menuOptions = new LinkedHashMap<>();
        menuOptions.put(PRINT_USER_ARTICLES, () -> articleView.printArticles(articleController.getArticlesForUser(user.getUsername())));
        menuOptions.put(PRINT_CATEGORY_ARTICLES, () -> printCategoryArticles(user));
        menuOptions.put(ADD_ARTICLE, () -> addArticle(user));
        menuOptions.put(MODIFY_ARTICLE_STATE, () -> editArticleState(user));
        return menuOptions;
    }

    @Override
    protected AbstractView getView() {
        return articleView;
    }

    /**
     * Stampa lista di articoli di una certa categoria (leaf)
     */
    public void printCategoryArticles(User user) {
        categoryMVController.printHierarchies();
        String rootCategoryName = categoryMVController.askRootName();
        String leafCategoryName = categoryMVController.askLeafName(rootCategoryName);
        articleView.printArticles(articleController.getArticlesForCategory(rootCategoryName, leafCategoryName, user.isAdmin()));
    }

    /**
     * Inserimento di un articolo da tastiera
     *
     * @param user Utente per il quale si vuole inserire un articolo
     */
    private void addArticle(User user) {
        assert !user.isAdmin() : ASSERTION_CONFIGURATOR_ADD_ARTICLE;
        categoryMVController.printHierarchies();
        String rootCategoryName = categoryMVController.askRootName();
        String leafCategoryName = categoryMVController.askLeafName(rootCategoryName);
        Map<String, String> fieldValues = categoryMVController.askFieldValues(rootCategoryName, leafCategoryName);
        articleController.addArticle(user.getUsername(), rootCategoryName, leafCategoryName, ArticleState.OFFERTA_APERTA, fieldValues);
    }


    /**
     * Viene chiesta la modifica dello stato di un articolo
     *
     * @param user Utente che possiede l'articolo a cui va modificato lo stato
     */
    private void editArticleState(User user) {
        List<Article> editableArticles = articleController.getArticlesEditableForUser(user.getUsername());
        if (editableArticles.isEmpty()) {
            articleView.message(ERROR_NO_UPDATABLE_ARTICLES);
            return;
        }
        Article selectedArticle = articleView.selectOptionFromCollection(editableArticles);
        articleView.printArticle(selectedArticle);
        articleController.updateState(selectedArticle.getId(), articleView.askArticleState(selectedArticle.isAvailable()));
    }

    public Article selectProposal(User user) {
        List<Article> articlesAvailable = articleController.getArticlesAvailableForUser(user.getUsername());
        if (articlesAvailable.isEmpty()) return null;
        return articleView.selectOptionFromCollection(articlesAvailable);
    }

    public Article selectWanted(User user, Article articleProposed) {
        List<Article> availableArticlesForExchange = articleController.getAvailableArticlesForExchange(user, articleProposed.getCategory());
        if (availableArticlesForExchange.isEmpty()) return null;
        return articleView.selectOptionFromCollection(availableArticlesForExchange);

    }
}
