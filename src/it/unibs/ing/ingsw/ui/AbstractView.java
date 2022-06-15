package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.Exchange;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;

import java.util.Map;

public abstract class AbstractView {

    /**
     * Metodo che visualizza indentato il nome della categoria e tutte le sotto categorie
     *
     * @param initialPrefixNumber Numero di ripetizioni della stringa prefissa inizialmente
     */
    private String onlyNameToString(Category category, int initialPrefixNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%s %s%n", " --> ".repeat(initialPrefixNumber), category.getName(), category.getFields().values()));
        for (Category child : category.getChildren().values()) {
            sb.append(onlyNameToString(child, initialPrefixNumber + 1));
        }
        return sb.toString();
    }

    public String renderCategory(Category category) {
        return onlyNameToString(category, 0);
    }

    public String renderArticle(Article article) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID articolo: %d\n\t%s:%s\n", article.getId(), article.getCategory().getName(), article.getState()));
        for (Map.Entry<String, String> field : article.getFields().entrySet()) {
            sb.append(String.format("\t%s=%s\n", field.getKey(), field.getValue()));
        }
        return sb.toString();
    }

    public String renderExchange(Exchange exchange) {
        String whereAndWhen = exchange.areExchanging() ? String.format(" (a %s in data %s)", exchange.getProposedWhere(), exchange.getProposedWhen()) : "";
        return String.format("Il %s qualcuno ha proposto lo scambio" + whereAndWhen + ":\n\t%s\n\t%s\n",
                exchange.getWhenLastEvent(),
//                renderArticle(exchange.getArticleWanted()),
                exchange.getArticleWanted().getFields(),
//                renderArticle(exchange.getArticleProposed()),
                exchange.getArticleProposed().getFields());
    }

    public String renderConfig(Config config) {
        return "Config{" +
                "piazza='" + config.getSquare() + '\'' +
                ", luoghi=" + config.getPlaces() +
                ", giorni=" + config.getDays() +
                ", intervalli orari=" + config.getTimeIntervals() +
                ", scadenza=" + config.getDeadline() +
                '}';
    }
}
