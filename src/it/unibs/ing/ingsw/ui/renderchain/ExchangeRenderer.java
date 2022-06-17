package it.unibs.ing.ingsw.ui.renderchain;

import it.unibs.ing.ingsw.article.Exchange;

public class ExchangeRenderer extends AbstractRenderer{
    public ExchangeRenderer(AbstractRenderer next) {
        super(next);
    }

    @Override
    public String render(Object o) {
        if (o instanceof Exchange exchange) {
            String whereAndWhen = exchange.areExchanging() ? String.format(" (a %s in data %s)", exchange.getProposedWhere(), exchange.getProposedWhen()) : "";
            String articleWantedParent = exchange.getArticleWanted().getCategory().getParent() != null ? exchange.getArticleWanted().getCategory().getParent().getName()+" -> " : "";
            String articleProposedParent = exchange.getArticleProposed().getCategory().getParent() != null ? exchange.getArticleProposed().getCategory().getParent().getName()+" -> " : "";
            return String.format("Il %s qualcuno ha proposto lo scambio" + whereAndWhen + ":\n\tCategoria: %s -> %s\n\t\t%s\n\tCategoria: %s -> %s\n\t\t%s\n",
                    exchange.getWhenLastEvent(),
                    articleWantedParent,
                    exchange.getArticleWanted().getCategory().getName(),
                    exchange.getArticleWanted().getFields(),
                    articleProposedParent,
                    exchange.getArticleProposed().getCategory().getName(),
                    exchange.getArticleProposed().getFields());
        }
        return next.render(o);
    }
}
