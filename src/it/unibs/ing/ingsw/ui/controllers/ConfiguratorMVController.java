package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.ui.views.AbstractView;
import it.unibs.ing.ingsw.ui.views.ConfiguratorView;

import java.util.LinkedHashMap;

public class ConfiguratorMVController extends AbstractMVController {
    private static final String MANAGE_CATEGORIES = "Gestisci categorie";
    private static final String MANAGE_CONFIG = "Gestisci configurazione generale";
    private static final String SEE_LEAF_OFFERS = "Mostra le offerte per una categoria foglia";
    private final CategoryMVController categoryMVController;
    private final ConfigMVController configMVController;
    private final ArticleMVController articleMVController;
    private final ConfiguratorView configuratorView;

    public ConfiguratorMVController(DataContainer saves, InputProvider inputProvider) {
        categoryMVController = new CategoryMVController(saves, inputProvider);
        configMVController = new ConfigMVController(saves, inputProvider);
        articleMVController = new ArticleMVController(saves, inputProvider);
        configuratorView = new ConfiguratorView(inputProvider);
    }

    @Override
    protected void beforeExecute() {}

    @Override
    protected LinkedHashMap<String, Runnable> getMenuOptions(User user) {
        LinkedHashMap<String, Runnable> menuOptions = new LinkedHashMap<>();
        menuOptions.put(MANAGE_CATEGORIES, () -> categoryMVController.execute(user));
        menuOptions.put(MANAGE_CONFIG, () -> configMVController.execute(user));
        menuOptions.put(SEE_LEAF_OFFERS, () -> articleMVController.printCategoryArticles(user));
        return menuOptions;
    }

    @Override
    protected AbstractView getView() {
        return configuratorView;
    }

}
