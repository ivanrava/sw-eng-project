package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.config.ConfigController;
import it.unibs.ing.ingsw.config.TimeInterval;
import it.unibs.ing.ingsw.io.Saves;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class ExchangeView {
    private final ExchangeController exchangeController;
    private final ConfigController configController;
    public static final String MENU_TITLE = "Gestione scambi";
    public static final String[] VOCI = {
            "Mostra i baratti che ti sono stati proposti",
            "Mostra i tuoi articoli in scambio (e ultime risposte)",
    };

    public ExchangeView(Saves saves) {
        exchangeController = new ExchangeController(saves);
        configController = new ConfigController(saves);
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

    private void printUpdateProposal(User user){  //FIXME
        int sizeOfExchanges = exchangeController.getExchanges(user).size();
        int index;
        Set<String> luoghi = configController.getLuoghi();
        Set<TimeInterval> timeIntervals = configController.getTimeIntervals();
        String proposedWhere;
        LocalDateTime proposedWhen;
        do {
           index = InputDati.leggiInteroConMinimo("inserisci indice scambio da selezionare:", 1) - 1;
        }while(index > sizeOfExchanges);
        proposedWhere = askProposedWhere(luoghi);
        proposedWhen = askProposedWhen(timeIntervals);
        exchangeController.updateProposal(proposedWhere, proposedWhen, user, index);
    }

    private String askProposedWhere(Set<String> luoghi){
        String proposedWhere;
        do {
            proposedWhere = InputDati.leggiStringaNonVuota("inserisci nuovo luogo:");
            if(!luoghi.contains(proposedWhere))
                System.out.println("devi inserire un luogo che esiste nella configurazione");
        }while(!luoghi.contains(proposedWhere));
        return proposedWhere;
    }

    private LocalDateTime askProposedWhen(Set<TimeInterval> timeIntervals){ //FIXME
        LocalDateTime proposedWhen ;
        LocalTime proposedHourAndMinute;
        int hour;
        int minute;
        boolean correct = false;
        do {
            hour = InputDati.leggiIntero("inserisci ora:", 0, 23);
            minute = InputDati.leggiIntero("inserisci minuto:", 0, 60);
            proposedHourAndMinute = LocalTime.of(hour, minute);
            for (TimeInterval localTime : configController.getTimeIntervals()) {
                correct = localTime.contains(proposedHourAndMinute);
            }
        }while(!correct);
        month = ... //inserimento mese
        year = ... //inserimento anno
        day  = ... //inserimento giorno
        proposedWhen =  LocalDateTime.of(year,month,day,hour,minute); //FIXME cambiare tipo?!
        return proposedWhen;
    }



}
