package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.config.ConfigController;
import it.unibs.ing.ingsw.config.TimeInterval;
import it.unibs.ing.ingsw.io.Saves;

import java.time.DayOfWeek;
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

    public void AskmodifyProposal(User user){
        printExchangingArticles(user);
        boolean scelta=InputDati.yesOrNo("vuoi accedere a uno dei baratti(per accettare/modificare appuntamento)?");
        if(scelta){
            askUpdateProposal(user);
        }
        else return;
    }


    private void askUpdateProposal(User user){  //FIXME
        int sizeOfExchanges = exchangeController.getExchanges(user).size();
        int index;
        Set<String> luoghi = configController.getLuoghi();
        Set<TimeInterval> timeIntervals = configController.getTimeIntervals();
        String proposedWhere;
        LocalDateTime proposedWhen;
        do {
           index = InputDati.leggiInteroConMinimo("inserisci il numero del baratto da selezionare:", 1) - 1;
        }while(index > sizeOfExchanges);
        User toUser = exchangeController.getToUser(user,index);
        if(toUser.equals(user)) {
            boolean scelta = InputDati.yesOrNo("vuoi accettare il luogo/tempo del baratto?");
            if (scelta) {
                exchangeController.acceptProposal(index, user);
            } else {
                proposedWhere = askProposedWhere(luoghi);
                proposedWhen = askProposedWhen(timeIntervals);
                exchangeController.updateProposal(proposedWhere, proposedWhen, user, index);
            }
        }
        else{
            System.out.println("devi aspettare la risposta dell'altro user...");
            return;
        }
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
        LocalTime proposedHourAndMinute = askHourAndMinute(timeIntervals);
        LocalDate proposedDayMonthYear = askDayMonthYear();
        LocalDateTime proposedWhen =  LocalDateTime.of(proposedDayMonthYear.getYear(), proposedDayMonthYear.getMonth(),proposedDayMonthYear.getDayOfMonth(),proposedHourAndMinute.getHour(),proposedHourAndMinute.getMinute());
        return proposedWhen;
    }

    private LocalDate askDayMonthYear(){
        LocalDate proposedDayMonthYear;
        int year;
        int month;
        int day;
        DayOfWeek dayOfWeek;
        do {
            year = InputDati.leggiIntero("inserisci anno:", 2022, 2030);
            month = InputDati.leggiIntero("inserisci numero mese:", 1, 12);
            day = InputDati.leggiIntero("inserisci giorno del mese:", 1, 31); //TODO implementare tutti i controlli
            proposedDayMonthYear = LocalDate.of(year,month,day);
            dayOfWeek=proposedDayMonthYear.getDayOfWeek();
            if(!configController.getDays().contains(dayOfWeek)){
                System.out.println("giorno della settimana non esistente");
            }
        }while(!configController.getDays().contains(dayOfWeek));
        return proposedDayMonthYear;
    }

    private LocalTime askHourAndMinute(Set<TimeInterval> timeIntervals){
        int hour;
        int minute;
        LocalTime proposedHourAndMinute;
        boolean correct=false;
        do {
            hour = InputDati.leggiIntero("inserisci ora:", 0, 23);
            minute = InputDati.leggiIntero("inserisci minuto:", 0, 60);
            proposedHourAndMinute = LocalTime.of(hour, minute);
            for (TimeInterval timeInterval : timeIntervals) {
                if(timeInterval.contains(proposedHourAndMinute)) correct = true;
            }
            if(!correct){
                System.out.println("luogo non esistente!");
            }
        }while(!correct);
        return proposedHourAndMinute;
    }



}
