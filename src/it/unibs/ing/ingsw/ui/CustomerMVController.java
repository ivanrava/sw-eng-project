package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.article.ArticleMVController;
import it.unibs.ing.ingsw.article.ExchangeMVController;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryMVController;
import it.unibs.ing.ingsw.config.ConfigMVController;
import it.unibs.ing.ingsw.io.DataContainer;

import java.time.Clock;
import java.util.LinkedHashMap;
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
        exchangeMVController = new ExchangeMVController(saves, inputProvider, Clock.systemUTC());
        customerView = new CustomerView(inputProvider);
    }

    @Override
    protected void beforeExecute() {}

    @Override
    protected LinkedHashMap<String, Runnable> getMenuOptions(User user) {
        LinkedHashMap<String, Runnable> menuOptions = new LinkedHashMap<>();
        menuOptions.put(SEE_CATEGORIES, categoryMVController::printHierarchies);
        menuOptions.put(SEE_CONFIG, configMVController::printConfig);
        menuOptions.put(MANAGE_ARTICLES, () -> articleMVController.execute(user));
        menuOptions.put(MANAGE_EXCHANGES, () -> exchangeMVController.execute(user));
        return menuOptions;
    }

    @Override
    protected AbstractView getView() {
        return customerView;
    }
}
