package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.article.ArticleView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.Saves;

public class CustomerView {
    private static final String MENU_TITLE = "Benvenuto Utente";
    private static final String SEE_CATEGORIES = "Visualizza categorie";
    private static final String SEE_CONFIG = "Visualizza generalità";
    public static final String MANAGE_ARTICLES = "Gestisci gli articoli";
    private final CategoryView categoryView;
    private final ConfigView configView;
    private final ArticleView articleView;

    public CustomerView(Saves saves) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(saves);
        articleView = new ArticleView(saves);
    }

    /**
     * Esegui l'UI del fruitore
     */
    public void execute(User user) {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, new String[] {
                SEE_CATEGORIES,
                SEE_CONFIG,
                MANAGE_ARTICLES
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.printHierarchies();
                case 2 -> configView.printConfig();
                case 3 -> articleView.execute(user);
            }
        }while (scelta != 0);
    }
}
