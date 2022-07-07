package it.unibs.ing.ingsw.ui.views.renderchain;

import it.unibs.ing.ingsw.domain.business.Article;

import java.util.Map;

public class ArticleRenderer implements SelectableRenderer {
    @Override
    public String render(Object o) {
        Article article = (Article) o;
        StringBuilder sb = new StringBuilder();
        String parent = article.getCategory().getParent() != null ? article.getCategory().getParent().getName() + " -> " : "";
        sb.append(String.format("ID articolo: %d\n\t" + parent + "%s:%s\n", article.getId(), article.getCategory().getName(), article.getState()));
        for (Map.Entry<String, String> field : article.getFields().entrySet()) {
            sb.append(String.format("\t%s=%s\n", field.getKey(), field.getValue()));
        }
        return sb.toString();
    }

    @Override
    public boolean canHandle(Object o) {
        return o instanceof Article;
    }
}
