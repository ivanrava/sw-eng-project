package it.unibs.ing.ingsw.ui.views.renderchain;

public class DefaultRenderer extends AbstractRenderer{
    public DefaultRenderer(AbstractRenderer next) {
        super(next);
    }

    @Override
    public String render(Object o) {
        return o.toString();
    }
}
