package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Article;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SaveArticles extends AbstractSave<Map<Integer, Article>> {

    public SaveArticles(String filename) throws IOException, ClassNotFoundException {
        super(filename);
    }

    public Map<Integer, Article> getArticles() {
        return getSaveObject();
    }

    @Override
    public Map<Integer, Article> defaultSaveObject() {
        return new HashMap<>();
    }
}
