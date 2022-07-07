package it.unibs.ing.ingsw.ui.views.renderers;

import it.unibs.ing.ingsw.domain.business.Exchange;

public class ExchangeRenderer implements SelectableRenderer {
    @Override
    public String render(Object o) {
        Exchange exchange = (Exchange) o;
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

    @Override
    public boolean canHandle(Object o) {
        return o instanceof Exchange;
    }
}
