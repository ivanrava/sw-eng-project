package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.article.ArticleView;
import it.unibs.ing.ingsw.article.ExchangeView;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.config.ConfigView;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.io.Saves;

public class CustomerView {
    private static final String MENU_TITLE = "Benvenuto Utente";
    private static final String SEE_CATEGORIES = "Visualizza categorie";
    private static final String SEE_CONFIG = "Visualizza generalità";
    public static final String MANAGE_ARTICLES = "Gestisci gli articoli";
    public static final String MANAGE_EXCHANGES = "Gestisci i baratti";
    private final CategoryView categoryView;
    private final ConfigView configView;
    private final ArticleView articleView;
    private final ExchangeView exchangeView;

    public CustomerView(DataContainer saves) {
        categoryView = new CategoryView(saves);
        configView = new ConfigView(saves);
        articleView = new ArticleView(saves);
        exchangeView = new ExchangeView(saves);
    }

    /**
     * Esegui l'UI del fruitore
     */
    public void execute(User user) {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, new String[] {
                SEE_CATEGORIES,
                SEE_CONFIG,
                MANAGE_ARTICLES,
                MANAGE_EXCHANGES
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> categoryView.printHierarchies();
                case 2 -> configView.printConfig();
                case 3 -> articleView.execute(user);
                case 4 -> exchangeView.execute(user);
            }
        }while (scelta != 0);
    }
}
