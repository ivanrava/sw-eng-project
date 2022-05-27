package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Article;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveArticles extends AbstractSave<Map<Integer, Article>> {
    public static final String SAVE_ARTICLES = "./articles.dat";

    public SaveArticles() throws IOException, ClassNotFoundException {}

    @Override
    protected String getSaveFilename() {
        return SAVE_ARTICLES;
    }

    public Map<Integer, Article> getArticles() {
        return getSaveObject();
    }

    @Override
    public Map<Integer, Article> defaultSaveObject() {
        return new HashMap<>();
    }
}
