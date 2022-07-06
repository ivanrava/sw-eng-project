package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.article.ArticleMVController;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryMVController;
import it.unibs.ing.ingsw.config.ConfigMVController;
import it.unibs.ing.ingsw.io.DataContainer;

import java.util.Map;

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
    protected Map<String, Runnable> getMenuOptions(User user) {
        return Map.of(
                MANAGE_CATEGORIES, () -> categoryMVController.execute(user),
                MANAGE_CONFIG, () -> configMVController.execute(user),
                SEE_LEAF_OFFERS, () -> articleMVController.printCategoryArticles(user)
        );
    }

    @Override
    protected AbstractView getView() {
        return configuratorView;
    }

}
