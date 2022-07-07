package it.unibs.ing.ingsw.ui.views;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.ui.views.renderchain.AbstractRenderer;
import it.unibs.ing.ingsw.ui.views.renderchain.ArticleRenderer;
import it.unibs.ing.ingsw.ui.views.renderchain.CategoryRenderer;
import it.unibs.ing.ingsw.ui.views.renderchain.ConfigRenderer;
import it.unibs.ing.ingsw.ui.views.renderchain.DefaultRenderer;
import it.unibs.ing.ingsw.ui.views.renderchain.ExchangeRenderer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractView {
    private static final String ASSERT_EMPTY_COLLECTION_MENU = "Stai creando un menu per una collezione vuota";
    private static final String INPUT_MENU_GENERIC_PROMPT = "Seleziona l'opzione desiderata: ";
    protected InputProvider inputProvider;
    protected String MENU_TITLE = "Menu";

    private final AbstractRenderer chain = new ArticleRenderer(new CategoryRenderer(new ConfigRenderer(new ExchangeRenderer(new DefaultRenderer(null)))));

    protected AbstractView(InputProvider inputProvider) {
        this.inputProvider = inputProvider;
    }

    protected String render(Object o) {
        return chain.render(o);
    }

    protected <T> String renderAll(Collection<T> collection) {
        return collection.stream().map(this::render).collect(Collectors.joining("\n"));
    }

    public String menu(Set<String> menuOptions) {
        String[] choices = menuOptions.toArray(new String[0]);
        MyMenu mainMenu = new MyMenu(MENU_TITLE, choices, inputProvider);

        int scelta;
        do {
            scelta = mainMenu.scegli();
            if (scelta > 0) return choices[scelta - 1];
        } while (scelta != 0);
        return null;
    }

    public void message(String message) {
        System.out.println(message);
    }

    /**
     * Stampa un menu di selezione per le opzioni di una collezione
     *
     * @param collection Collezione da cui selezionare un oggetto
     * @param <T>        Tipo della collezione
     * @return Opzione scelta della collezione
     */
    public <T> T selectOptionFromCollection(Collection<T> collection) {
        assert !collection.isEmpty() : ASSERT_EMPTY_COLLECTION_MENU;
        Map<Integer, T> map = new HashMap<>();
        for (T element : collection) {
            map.put(map.size() + 1, element);
        }
        map.forEach((id, option) -> System.out.printf("%d -> %s\n", id, render(option)));
        int id = inputProvider.leggiIntero(INPUT_MENU_GENERIC_PROMPT, 1, map.size());
        return map.get(id);
    }
}
