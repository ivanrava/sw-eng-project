package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.article.ArticleMVController;
import it.unibs.ing.ingsw.article.ExchangeMVController;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryMVController;
import it.unibs.ing.ingsw.config.ConfigMVController;
import it.unibs.ing.ingsw.io.DataContainer;

import java.util.Map;

public class CustomerMVController extends AbstractMVController {
    private static final String SEE_CATEGORIES = "Visualizza categorie";
    private static final String SEE_CONFIG = "Visualizza generalità";
    public static final String MANAGE_ARTICLES = "Gestisci gli articoli";
    public static final String MANAGE_EXCHANGES = "Gestisci i baratti";
    private final CategoryMVController categoryMVController;
    private final ConfigMVController configMVController;
    private final ArticleMVController articleMVController;
    private final ExchangeMVController exchangeMVController;
    private final CustomerView customerView;

    public CustomerMVController(DataContainer saves, InputProvider inputProvider) {
        categoryMVController = new CategoryMVController(saves, inputProvider);
        configMVController = new ConfigMVController(saves, inputProvider);
        articleMVController = new ArticleMVController(saves, inputProvider);
        exchangeMVController = new ExchangeMVController(saves, inputProvider);
        customerView = new CustomerView(inputProvider);
    }

    @Override
    protected void beforeExecute() {}

    @Override
    protected Map<String, Runnable> getMenuOptions(User user) {
        return Map.of(
                SEE_CATEGORIES, categoryMVController::printHierarchies,
                SEE_CONFIG, configMVController::printConfig,
                MANAGE_ARTICLES, () -> articleMVController.execute(user),
                MANAGE_EXCHANGES, () -> exchangeMVController.execute(user)
        );
    }

    @Override
    protected AbstractView getView() {
        return customerView;
    }
}
