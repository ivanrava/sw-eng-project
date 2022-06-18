package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.ui.renderchain.AbstractRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ArticleRenderer;
import it.unibs.ing.ingsw.ui.renderchain.CategoryRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ConfigRenderer;
import it.unibs.ing.ingsw.ui.renderchain.DefaultRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ExchangeRenderer;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class AbstractView {

    private final AbstractRenderer chain =
            new ArticleRenderer(
                    new CategoryRenderer(
                            new ConfigRenderer(
                                    new ExchangeRenderer(
                                            new DefaultRenderer(null)
                                    )
                            )
                    )
            );

    protected String render(Object o) {
        return chain.render(o);
    }

    protected <T> String renderAll(Collection<T> collection) {
        return collection.stream().map(this::render).collect(Collectors.joining("\n"));
    }
}
