package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.ui.renderchain.AbstractRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ArticleRenderer;
import it.unibs.ing.ingsw.ui.renderchain.CategoryRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ConfigRenderer;
import it.unibs.ing.ingsw.ui.renderchain.DefaultRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ExchangeRenderer;

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

}
