package it.unibs.ing.ingsw.ui;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.ui.renderchain.AbstractRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ArticleRenderer;
import it.unibs.ing.ingsw.ui.renderchain.CategoryRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ConfigRenderer;
import it.unibs.ing.ingsw.ui.renderchain.DefaultRenderer;
import it.unibs.ing.ingsw.ui.renderchain.ExchangeRenderer;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractView {
    protected String MENU_TITLE = "Menu";

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

    public String menu(Set<String> menuOptions) {
        String[] choices = menuOptions.toArray(new String[0]);
        MyMenu mainMenu = new MyMenu(MENU_TITLE, choices);

        int scelta;
        do {
            scelta = mainMenu.scegli();
            if (scelta > 0)
                return choices[scelta - 1];
        } while (scelta != 0);
        return null;
    }

    public void message(String message) {
        System.out.println(message);
    }
}
