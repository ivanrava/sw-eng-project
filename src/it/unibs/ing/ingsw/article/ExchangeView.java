package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.io.Saves;

public class ExchangeView {
    private final ExchangeController exchangeController;
    public static final String MENU_TITLE = "Gestione scambi";
    public static final String[] VOCI = {
            "Mostra i baratti che ti sono stati proposti",
            "Mostra i tuoi articoli in scambio (e ultime risposte)",
    };

    public ExchangeView(Saves saves) {
        exchangeController = new ExchangeController(saves);
    }

    private class MenuOption {
        private String option;
        private Runnable behavior;

        public MenuOption(String option, Runnable behavior) {
            this.option = option;
            this.behavior = behavior;
        }
    }

    /**
     * Esegue la vista
     * @param user Utente che la esegue
     */
    public void execute(User user) {
        /*
        HashMap<Integer, MenuOption> menu = new HashMap<>();

        if (exchangeController.hasOpenArticles(user))
            menu.put(menu.size()+1, new MenuOption("Proponi un baratto", () -> proposeExchange()));
        if (exchangeController.hasSelectedArticles(user))
            menu.put(menu.size()+1, new MenuOption("Accetta le offerte di baratto", () -> acceptExchangeOffers()));
        if (exchangeController.hasExchangingArticles(user))
            menu.put(menu.size()+1, new MenuOption("Conferma i baratti", () -> acceptExchanges()));
         */

        MyMenu mainMenu = new MyMenu(MENU_TITLE, VOCI);

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> printProposedExchanges(user);
                case 2 -> printExchangingArticles(user);
            }
        }while (scelta != 0);
    }

    /**
     * Stampa i baratti proposti all'utente
     * @param user L'utente di cui visualizzare i baratti proposti
     */
    private void printProposedExchanges(User user) {
        exchangeController.getProposals(user).forEach(System.out::println);
    }

    /**
     * Stampa gli articoli in scambio (e quindi i baratti concordati sugli articoli)
     * @param user L'utente di cui visualizzare gli articoli in scambio / baratti concordati sugli articoli
     */
    private void printExchangingArticles(User user) {
        exchangeController.getExchanges(user).forEach(System.out::println);
    }
}
