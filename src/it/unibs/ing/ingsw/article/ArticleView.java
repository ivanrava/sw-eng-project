package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.util.List;

public class ArticleView extends AbstractView {
    private static final String ASK_AVAILABLE_ARTICLE = "Vuoi rendere disponibile l'articolo?";
    private static final String ASK_RETIRE_ARTICLE = "Vuoi ritirare l'articolo?";
    protected String MENU_TITLE = "Gestione articoli";

    protected ArticleView(InputProvider inputProvider) {
        super(inputProvider);
    }

    public void printArticles(List<Article> articlesForUser) {
        message(renderAll(articlesForUser));
    }

    public void printArticle(Article article) {
        message(render(article));
    }

    /**
     * Viene chiesto lo stato di un certo articolo
     *
     * @param isAvailable indica se l'articolo é disponibile o meno per uno scambio
     * @return Stato di un articolo
     */
    public ArticleState askArticleState(boolean isAvailable) {
        if (isAvailable) {
            boolean scelta = inputProvider.yesOrNo(ASK_RETIRE_ARTICLE);
            return scelta ? ArticleState.OFFERTA_RITIRATA : ArticleState.OFFERTA_APERTA;
        } else {
            boolean scelta = inputProvider.yesOrNo(ASK_AVAILABLE_ARTICLE);
            return scelta ? ArticleState.OFFERTA_APERTA : ArticleState.OFFERTA_RITIRATA;
        }
    }
}
