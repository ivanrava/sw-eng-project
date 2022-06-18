package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.CategoryController;
import it.unibs.ing.ingsw.category.CategoryView;
import it.unibs.ing.ingsw.category.Field;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.util.HashMap;
import java.util.Map;

public class ArticleView extends AbstractView {
    public static final String ASSERTION_CONFIGURATOR_ADD_ARTICLE = "Un configuratore non dovrebbe poter aggiungere articoli!";
    public static final String ASK_FIELD_OBBLIGATORIO = "%s (obbligatorio): ";
    public static final String ASK_FIELD_FACOLTATIVO = "%s (facoltativo): ";
    public static final String INSERT_ID = "Inserisci l'id numerico dell'articolo da modificare: ";
    public static final String ERROR_ID_UNEXISTANT = "Non esiste un articolo identificato da questo numero :(";
    public static final String ASK_RETIRE_ARTICLE = "Vuoi ritirare l'articolo?";
    public static final String ASK_AVAILABLE_ARTICLE = "Vuoi rendere disponibile l'articolo?";
    private final ArticleController articleController;
    private final CategoryController categoryController;
    private final CategoryView categoryView;
    public static final String MENU_TITLE = "Gestione articoli";
    public static final String[] VOCI = {
            "Visualizza articoli/offerte dell'utente",
            "Visualizza articoli/offerte di una categoria",
            "Aggiungi un articolo/offerta",
            "Modifica stato di un'offerta"
    };

    /**
     * Costruttore principale
     * @param saves Istanza dei salvataggi dell'applicazione
     */
    public ArticleView(DataContainer saves) {
        articleController = new ArticleController(saves);
        categoryController = new CategoryController(saves);
        categoryView = new CategoryView(saves);
    }

    /**
     * Esegui l'UI generale degli articoli
     * @param user Utente che esegue la view
     */
    public void execute(User user) {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, VOCI);

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> System.out.println(renderAll(articleController.getArticlesForUser(user.getUsername())));
                case 2 -> printCategoryArticles(user);
                case 3 -> addArticle(user);
                case 4 -> editArticleState(user);
            }
        }while (scelta != 0);
    }

    /**
     * Stampa lista di articoli di una certa categoria (leaf)
     */
    private void printCategoryArticles(User user) {
        categoryView.printHierarchies();
        String rootCategoryName = categoryView.askAndCheckRootName();
        String leafCategoryName = categoryView.askAndCheckLeafName(rootCategoryName);
        System.out.println(renderAll(articleController.getArticlesForCategory(rootCategoryName, leafCategoryName, user.isAdmin())));
    }

    /**
     * Inserimento di un articolo da tastiera
     * @param user Utente per il quale si vuole inserire un articolo
     */
    private void addArticle(User user) {
        assert !user.isAdmin() : ASSERTION_CONFIGURATOR_ADD_ARTICLE;
        categoryView.printHierarchies();
        String rootCategoryName = categoryView.askAndCheckRootName();
        String leafCategoryName = categoryView.askAndCheckLeafName(rootCategoryName);
        Map<String, String> fieldValues = askFieldValues(categoryController.getFieldsForCategory(rootCategoryName, leafCategoryName));
        articleController.addArticle(user.getUsername(), rootCategoryName, leafCategoryName, ArticleState.OFFERTA_APERTA, fieldValues);
    }

    /**
     * Chiede l'inserimento dei valori dei campi di un articolo
     * @param categoryFields Campi richiesti da un articolo che appartiene a una certa categoria
     * @return Map che ha come chiave il nome del campo, e come valore il valore del campo
     */
    private Map<String, String> askFieldValues(Map<String, Field> categoryFields) {
        Map<String, String> fieldValues = new HashMap<>();
        String value;
        for (Map.Entry<String, Field> entry: categoryFields.entrySet()) {
            if (entry.getValue().isRequired()) {
                value = InputDati.leggiStringaNonVuota(String.format(ASK_FIELD_OBBLIGATORIO, entry.getKey()));
            } else {
                value = InputDati.leggiStringa(String.format(ASK_FIELD_FACOLTATIVO, entry.getKey()));
            }
            fieldValues.put(entry.getKey(), value);
        }
        return fieldValues;
    }

    /**
     * Viene chiesta la modifica dello stato di un articolo
     * @param user Utente che possiede l'articolo a cui va modificato lo stato
     */
    private void editArticleState(User user) {
        System.out.println(renderAll(articleController.getArticlesEditableForUser(user.getUsername())));
        int id;
        do {
            id = InputDati.leggiInteroConMinimo(INSERT_ID, 0);
            if (!articleController.isEditableArticle(id)) {
                System.out.println(ERROR_ID_UNEXISTANT);
            }
        } while (!articleController.isEditableArticle(id));
        Article selectedArticle = articleController.getArticle(id);
        System.out.println(render(selectedArticle));
        articleController.updateState(id, askArticleState(selectedArticle.isAvailable()));
    }

    /**
     * Viene chiesto lo stato di un certo articolo
     * @return Stato di un articolo
     */
    private ArticleState askArticleState(boolean isAvailable) {
        if (isAvailable){
            boolean scelta = InputDati.yesOrNo(ASK_RETIRE_ARTICLE);
            return scelta? ArticleState.OFFERTA_RITIRATA : ArticleState.OFFERTA_APERTA;
        }else {
            boolean scelta = InputDati.yesOrNo(ASK_AVAILABLE_ARTICLE);
            return scelta? ArticleState.OFFERTA_APERTA : ArticleState.OFFERTA_RITIRATA;
        }
    }
}
