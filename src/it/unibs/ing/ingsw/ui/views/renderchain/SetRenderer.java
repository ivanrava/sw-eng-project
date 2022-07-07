package it.unibs.ing.ingsw.ui.views.renderchain;

import java.util.LinkedList;

public class SetRenderer implements Renderer {
    private final LinkedList<SelectableRenderer> setOfRenderers;

    public SetRenderer() {
        setOfRenderers = new LinkedList<>();
        setOfRenderers.add(new ArticleRenderer());
        setOfRenderers.add(new CategoryRenderer());
        setOfRenderers.add(new ConfigRenderer());
        setOfRenderers.add(new ExchangeRenderer());
    }

    @Override
    public String render(Object o) {
        for (SelectableRenderer selRenderer : setOfRenderers) {
            if (selRenderer.canHandle(o)) return selRenderer.render(o);
        }
        return o.toString();
    }
}
