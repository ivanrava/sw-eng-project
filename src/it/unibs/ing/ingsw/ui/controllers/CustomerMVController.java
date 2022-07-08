package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.ui.views.AbstractView;
import it.unibs.ing.ingsw.ui.views.CustomerView;

import java.time.Clock;
import java.util.LinkedHashMap;

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

    public CustomerMVController(DataContainer saves, InputProvider inputProvider, Clock clock) {
        categoryMVController = new CategoryMVController(saves, inputProvider);
        configMVController = new ConfigMVController(saves, inputProvider);
        articleMVController = new ArticleMVController(saves, inputProvider);
        exchangeMVController = new ExchangeMVController(saves, inputProvider, clock);
        customerView = new CustomerView(inputProvider);
    }

    @Override
    protected boolean beforeExecute() {
        return true;
    }

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
