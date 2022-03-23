package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.article.ArticleView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.Saves;

public class ConfiguratorView {
    private static final String MENU_TITLE = "Interfaccia di amministrazione";
    private static final String MANAGE_CATEGORIES = "Gestisci categorie";
    private static final String MANAGE_CONFIG = "Gestisci configurazione generale";
    private static final String SEE_LEAF_OFFERS = "Mostra le offerte per una categoria foglia";
    private final CategoryView categoryView;
    private final ConfigView configView;
    private final ArticleView articleView;

    public ConfiguratorView(Saves saves) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(saves);
        articleView = new ArticleView(saves);
    }

    /**
     * Esegui l'UI specifica del configuratore
     */
    public void execute(User user) {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, new String[] {
                MANAGE_CATEGORIES,
                MANAGE_CONFIG,
                SEE_LEAF_OFFERS
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.execute();
                case 2 -> configView.execute();
                case 3 -> articleView.printCategoryArticles(user);
            }
        }while (scelta != 0);
    }

}
