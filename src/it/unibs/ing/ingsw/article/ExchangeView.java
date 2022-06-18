package it.unibs.ing.ingsw.article;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.config.ConfigController;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class ExchangeView extends AbstractView {
    private static final String ASSERT_EMPTY_COLLECTION_MENU = "Stai creando un menu per una collezione vuota";
    private static final String ASK_ACCEPT_BARTER = "Accetti il baratto proposto?";
    private static final String ASK_ACCEPT_APPOINTMENT = "Vuoi accettare il luogo/tempo del baratto? ";
    private static final String INPUT_WHERE_WHEN = "Ok, ora proponi un luogo / ora per lo scambio...";
    private static final String INPUT_MENU_GENERIC_PROMPT = "Seleziona l'opzione desiderata: ";
    private static final String INPUT_YEAR = "Inserisci l'anno: ";
    private static final String INPUT_MONTH = "Inserisci il mese [1-12]: ";
    private static final String INPUT_DAY = "Inserisci un giorno tra quelli validi: ";
    private static final String INPUT_HOUR = "Inserisci ora: ";
    private static final String INPUT_MINUTE = "Inserisci minuto: ";
    private static final String ERROR_UNEXISTANT_CONFIGURATION = "Configurazione inesistente :-(";
    private static final String ERROR_NO_OPEN_ARTICLES = "Non hai articoli da scambiare :(";
    private static final String ERROR_ADD_ARTICLE_BEFORE_PROPOSAL = "Aggiungi un articolo prima di proporre uno scambio";
    private static final String ERROR_NO_MATCHING_ARTICLES = "Non ci sono articoli con cui fare lo scambio :(";
    private static final String ERROR_NO_PROPOSALS = "Non hai proposte da gestire :-(";
    private static final String ERROR_NO_ARTICLES_EXCHANGE = "Non hai articoli in scambio :-(";
    private static final String ERROR_NO_DISCUSSIONS = "Non hai nessuna proposta da vagliare :(";
    private static final String ERROR_PAST_DATE = "La data è già passata :(";
    private static final String ERROR_INVALID_TIME = "Orario non ammesso dall'applicazione :(";
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

    public ExchangeView(DataContainer saves) {
        exchangeController = new ExchangeController(saves);
        configController = new ConfigController(saves);
        articleController = new ArticleController(saves);
    }

    /**
     * Esegue la vista
     * @param user Utente che la esegue
     */
    public void execute(User user) {
        if (!configController.existsDefaultValues()){
            System.out.println(ERROR_UNEXISTANT_CONFIGURATION);
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
            System.out.println(ERROR_NO_OPEN_ARTICLES);
            System.out.println(ERROR_ADD_ARTICLE_BEFORE_PROPOSAL);
            return;
        }
        Article articleProposed = selectOptionFromCollection(articlesAvailable);
        // Seleziona l'articolo desiderato
        List<Article> availableArticlesForExchange = articleController.getAvailableArticlesForExchange(user, articleProposed.getCategory());
        if (availableArticlesForExchange.isEmpty()) {
            System.out.println(ERROR_NO_MATCHING_ARTICLES);
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
            System.out.println(ERROR_NO_PROPOSALS);
            return;
        }
        Exchange selectedExchange = selectOptionFromCollection(proposalsForUser);
        System.out.println(render(selectedExchange));
        if(InputDati.yesOrNo(ASK_ACCEPT_BARTER)) {
            exchangeController.acceptProposal(selectedExchange);
            System.out.println(INPUT_WHERE_WHEN);
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
            System.out.println(ERROR_NO_ARTICLES_EXCHANGE);
        }
        System.out.println(renderAll(exchangingExchanges));
    }

    /**
     * Stampa un menu di selezione per le opzioni di una collezione
     * @param collection Collezione da cui selezionare un oggetto
     * @param <T> Tipo della collezione
     * @return Opzione scelta della collezione
     */
    private <T> T selectOptionFromCollection(Collection<T> collection) {
        assert !collection.isEmpty() : ASSERT_EMPTY_COLLECTION_MENU;
        Map<Integer, T> map = new HashMap<>();
        for (T element : collection){
            map.put(map.size()+1, element);
        }
        map.forEach((id, option) -> System.out.printf("%d -> %s\n", id, render(option)));
        int id = InputDati.leggiIntero(INPUT_MENU_GENERIC_PROMPT, 1, map.size());
        return map.get(id);
    }

    /**
     * Gestisce gli appuntamenti (dei baratti) di un utente
     * @param user Utente di cui gestire gli appuntamenti
     */
    private void manageAppointments(User user) {
        List<Exchange> exchangeList = exchangeController.getExchangesAwaitingForAnswer(user);
        if (exchangeList.isEmpty()) {
            System.out.println(ERROR_NO_DISCUSSIONS);
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
        System.out.println(render(exchange));
        if (InputDati.yesOrNo(ASK_ACCEPT_APPOINTMENT)) {
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
            int year = InputDati.leggiIntero(INPUT_YEAR, LocalDate.now().getYear(), LocalDate.now().getYear()+1);
            int month = InputDati.leggiIntero(INPUT_MONTH, 1, 12);
            int day = askDay(year, month);
            proposedDate = LocalDate.of(year, month, day);
            if (!proposedDate.isAfter(LocalDate.now())) {
                System.out.println(ERROR_PAST_DATE);
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
        Map<Integer, String> validDays = new TreeMap<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        for (int day = 1; day < yearMonth.lengthOfMonth(); day++) {
            DayOfWeek dayOfWeek = yearMonth.atDay(day).getDayOfWeek();
            if (configController.isValidDayOfWeek(dayOfWeek)) {
                validDays.put(day, dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
            }
        }

        // Chiedi un giorno valido
        validDays.forEach((day, dayOfWeek) -> System.out.println("\t"+dayOfWeek+' '+day));
        return InputDati.leggiInteroDaSet(INPUT_DAY, validDays.keySet());
    }

    /**
     * Chiedi un orario per il baratto
     * @return Orario per il baratto, validato
     */
    private LocalTime askTime(){
        LocalTime proposedTime;
        do {
            int hour = InputDati.leggiIntero(INPUT_HOUR, 0, 23);
            int minute = InputDati.leggiInteroDaSet(INPUT_MINUTE, configController.allowedMinutes());
            proposedTime = LocalTime.of(hour, minute);
            if(!configController.isValidTime(proposedTime)){
                System.out.println(ERROR_INVALID_TIME);
                configController.getTimeIntervals().forEach(timeInterval -> System.out.println(timeInterval.allowedTimes()));
            }
        } while(!configController.isValidTime(proposedTime));
        return proposedTime;
    }
}
