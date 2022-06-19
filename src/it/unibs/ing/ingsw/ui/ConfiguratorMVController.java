package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.article.ArticleView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.DataContainer;

import java.util.Map;

public class ConfiguratorMVController extends AbstractMVController {
    private static final String MANAGE_CATEGORIES = "Gestisci categorie";
    private static final String MANAGE_CONFIG = "Gestisci configurazione generale";
    private static final String SEE_LEAF_OFFERS = "Mostra le offerte per una categoria foglia";
    private final CategoryView categoryView;
    private final ConfigView configView;
    private final ArticleView articleView;

    public ConfiguratorMVController(DataContainer saves) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(saves);
        articleView = new ArticleView(saves);
    }

    @Override
    protected void beforeExecute() {}

    @Override
    protected Map<String, Runnable> getMenuOptions(User user) {
        return Map.of(
                MANAGE_CATEGORIES, categoryView::execute,
                MANAGE_CONFIG, configView::execute,
                SEE_LEAF_OFFERS, () -> articleView.printCategoryArticles(user)
        );
    }

    @Override
    protected AbstractView getView() {
        return new ConfiguratorView();
    }

}
