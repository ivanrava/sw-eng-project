package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.config.ConfigController;
import it.unibs.ing.ingsw.io.Saves;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class ExchangeView {
    private final ExchangeController exchangeController;
    private final ConfigController configController;
    private final ArticleController articleController;
    public static final String MENU_TITLE = "Gestione scambi";
    public static final String[] VOCI = {
            "Proponi un baratto",
            "Gestisci i baratti che ti sono stati proposti",
            "Mostra i tuoi articoli in scambio (e ultime risposte)",
            "Accetta/modifica luogo/tempo dei baratti"
    };

    public ExchangeView(Saves saves) {
        exchangeController = new ExchangeController(saves);
        configController = new ConfigController(saves);
        articleController = new ArticleController(saves);
    }

    /**
     * Esegue la vista
     * @param user Utente che la esegue
     */
    public void execute(User user) {
        //TODO: da vedere controllo
        if (!configController.existsDefaultValues()){
            System.out.println("Configurazione inesistente :-(");
            return;
        }
        //controllo sugli scambi in scadenza
        exchangeController.deleteExpiredExchanges();
        MyMenu mainMenu = new MyMenu(MENU_TITLE, VOCI);

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> proposeExchange(user);
                case 2 -> manageProposals(user);
                case 3 -> printExchangingArticles(user);
                case 4 -> manageAppointments(user);
            }
        }while (scelta != 0);
    }

    /**
     * Propone uno scambio, selezionando articolo proprio e altrui di ugual categoria
     * @param user Utente che propone lo scambio
     */
    private void proposeExchange(User user) {
        // Seleziona la proposta
        List<Article> articlesAvailable = articleController.getArticlesAvailableForUser(user.getUsername());
        if (articlesAvailable.isEmpty()) {
            System.out.println("Non hai articoli da scambiare :(");
            System.out.println("Aggiungi un articolo prima di proporre uno scambio");
            return;
        }
        Article articleProposed = selectOptionFromCollection(articlesAvailable);
        // Seleziona l'articolo desiderato
        List<Article> availableArticlesForExchange = articleController.getAvailableArticlesForExchange(user, articleProposed.getCategory());
        if (availableArticlesForExchange.isEmpty()) {
            System.out.println("Non ci sono articoli con cui fare lo scambio :(");
            return;
        }
        Article articleWanted = selectOptionFromCollection(availableArticlesForExchange);

        exchangeController.startExchange(articleProposed.getId(), articleWanted.getId());
    }

    /**
     * Chiede all'utente di gestire i baratti che gli sono stati proposti
     * @param user Utente di cui chiedere la proposta
     */
    private void manageProposals(User user) {
        List<Exchange> proposalsForUser = exchangeController.getProposalsForUser(user);
        if (proposalsForUser.isEmpty()){
            System.out.println("Non hai proposte da gestire :-(");
            return;
        }
        Exchange selectedExchange = selectOptionFromCollection(proposalsForUser);
        System.out.println(selectedExchange);
        if(InputDati.yesOrNo("Accetti il baratto proposto?")) {
            exchangeController.acceptProposal(selectedExchange);
            System.out.println("Ok, ora proponi un luogo / ora per lo scambio...");
            editAppointment(selectedExchange);
        } else {
            exchangeController.rejectProposal(selectedExchange);
        }
    }

    /**
     * Stampa gli articoli in scambio (e quindi i baratti concordati sugli articoli)
     * @param user L'utente di cui visualizzare gli articoli in scambio / baratti concordati sugli articoli
     */
    private void printExchangingArticles(User user) {
        List<Exchange> exchangingExchanges = exchangeController.getExchangingExchanges(user);
        if (exchangingExchanges.isEmpty()){
            System.out.println("Non hai articoli in scambio :-(");
        }
        exchangingExchanges.forEach(System.out::println);
    }

    /**
     * Stampa un menu di selezione per le opzioni di una collezione
     * @param collection Collezione da cui selezionare un oggetto
     * @param <T> Tipo della collezione
     * @return Opzione scelta della collezione
     */
    private <T> T selectOptionFromCollection(Collection<T> collection) {
        assert !collection.isEmpty() : "Stai creando un menu per una collezione vuota";
        Map<Integer, T> map = new HashMap<>();
        for (T element : collection){
            map.put(map.size()+1, element);
        }
        map.forEach((id, option) -> System.out.printf("%d -> %s\n", id, option));
        int id = InputDati.leggiIntero("Seleziona l'opzione desiderata: ", 1, map.size());
        return map.get(id);
    }

    /**
     * Gestisce gli appuntamenti (dei baratti) di un utente
     * @param user Utente di cui gestire gli appuntamenti
     */
    private void manageAppointments(User user) {
        List<Exchange> exchangeList = exchangeController.getExchangesAwaitingForAnswer(user);
        if (exchangeList.isEmpty()) {
            System.out.println("Non hai nessuna proposta da vagliare :(");
            return;
        }
        Exchange exchange = selectOptionFromCollection(exchangeList);
        askAppointmentConfirmation(exchange);
    }

    /**
     * Chiede conferma dell'appuntamento, e in caso contrario lo modifica
     * @param exchange Scambio per cui chiedere la conferma / modifica dell'appuntamento
     */
    private void askAppointmentConfirmation(Exchange exchange) {
        System.out.println(exchange);
        if (InputDati.yesOrNo("Vuoi accettare il luogo/tempo del baratto? ")) {
            exchangeController.acceptExchange(exchange);
        } else {
            editAppointment(exchange);
        }
    }

    /**
     * Modifica la proposta fatta
     * @param exchange Scambio di cui modificare la proposta
     */
    private void editAppointment(Exchange exchange) {
        String proposedWhere = selectWhere();
        LocalDateTime proposedWhen = askProposedWhen();
        exchangeController.updateAppointment(proposedWhere, proposedWhen, exchange);
    }

    /**
     * Seleziona un luogo valido
     * @return Luogo valido
     */
    private String selectWhere() {
        return selectOptionFromCollection(configController.getLuoghi());
    }

    /**
     * Chiedi l'orario per una proposta
     * @return Orario proposto
     */
    private LocalDateTime askProposedWhen() {
        LocalTime proposedTime = askTime();
        LocalDate proposedDate = askDate();
        return LocalDateTime.of(proposedDate, proposedTime);
    }

    /**
     * Chiede una data, validata
     * @return Data validata
     */
    private LocalDate askDate(){
        LocalDate proposedDate;
        do {
            int year = InputDati.leggiIntero("Inserisci l'anno: ", LocalDate.now().getYear(), LocalDate.now().getYear()+1);
            int month = InputDati.leggiIntero("Inserisci il mese [1-12]: ", 1, 12);
            int day = askDay(year, month);
            proposedDate = LocalDate.of(year, month, day);
            if (!proposedDate.isAfter(LocalDate.now())) {
                System.out.println("La data è già passata :(");
            }
        } while(!proposedDate.isAfter(LocalDate.now()));
        return proposedDate;
    }

    /**
     * Chiede un giorno del mese, ammesso dall'applicazione, dato l'anno e il mese
     * @param year Anno
     * @param month Mese
     * @return Giorno del mese, che ricade in un giorno della settimana ammesso dall'applicazione
     */
    private int askDay(int year, int month) {
        // Costruisce i giorni validi
        HashMap<Integer, String> validDays = new HashMap<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        for (int day = 1; day < yearMonth.lengthOfMonth(); day++) {
            DayOfWeek dayOfWeek = yearMonth.atDay(day).getDayOfWeek();
            if (configController.isValidDayOfWeek(dayOfWeek)) {
                validDays.put(day, dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
            }
        }

        // Chiedi un giorno valido
        validDays.forEach((day, dayOfWeek) -> System.out.println("\t"+dayOfWeek+' '+day));
        return InputDati.leggiInteroDaSet("Inserisci un giorno tra quelli validi: ", validDays.keySet());
    }

    /**
     * Chiedi un orario per il baratto
     * @return Orario per il baratto, validato
     */
    private LocalTime askTime(){
        LocalTime proposedTime;
        do {
            int hour = InputDati.leggiIntero("Inserisci ora: ", 0, 23);
            int minute = InputDati.leggiIntero("Inserisci minuto: ", 0, 59);
            proposedTime = LocalTime.of(hour, minute);
            if(!configController.isValidTime(proposedTime)){
                System.out.println("Orario non ammesso dall'applicazione :(");
                configController.getTimeIntervals().forEach(timeInterval -> System.out.println(timeInterval.allowedTimes()));
            }
        } while(!configController.isValidTime(proposedTime));
        return proposedTime;
    }
}
