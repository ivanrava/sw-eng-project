package it.unibs.ing.ingsw.ui.renderchain;

public abstract class AbstractRenderer {
    protected final AbstractRenderer next;

    public AbstractRenderer(AbstractRenderer next) {
        this.next = next;
    }

    public abstract String render(Object o);
}