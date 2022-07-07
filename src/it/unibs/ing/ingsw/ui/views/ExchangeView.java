package it.unibs.ing.ingsw.ui.views;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.config.TimeInterval;
import it.unibs.ing.ingsw.domain.business.Exchange;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class ExchangeView extends AbstractView {
    private static final String ASK_ACCEPT_BARTER = "Accetti il baratto proposto?";
    private static final String INPUT_YEAR = "Inserisci l'anno: ";
    private static final String INPUT_MONTH = "Inserisci il mese [1-12]: ";
    private static final String INPUT_DAY = "Inserisci un giorno tra quelli validi: ";
    private static final String INPUT_HOUR = "Inserisci ora: ";
    private static final String INPUT_MINUTE = "Inserisci minuto: ";
    private static final String ERROR_PAST_DATE = "La data è già passata :(";
    private static final String ERROR_INVALID_TIME = "Orario non ammesso dall'applicazione :(";
    private static final String ERROR_NO_ARTICLES_EXCHANGE = "Non hai articoli in scambio :-(";
    private static final String ASK_ACCEPT_APPOINTMENT = "Vuoi accettare il luogo/tempo del baratto? ";


    protected String MENU_TITLE = "Gestione scambi";

    public ExchangeView(InputProvider inputProvider) {
        super(inputProvider);
    }

    public void printExchange(Exchange exchange) {
        message(render(exchange));
    }

    public void printExchanges(Collection<Exchange> exchanges) {
        message(renderAll(exchanges));
    }

    /**
     * Stampa gli articoli in scambio (e quindi i baratti concordati sugli articoli)
     *
     * @param exchangingArticles articoli in scambio
     */
    public void printExchangingArticles(List<Exchange> exchangingArticles) {
        if (exchangingArticles.isEmpty()) {
            message(ERROR_NO_ARTICLES_EXCHANGE);
        }
        printExchanges(exchangingArticles);
    }

    public boolean askAcceptExchange() {
        return inputProvider.yesOrNo(ASK_ACCEPT_BARTER);
    }

    /**
     * Seleziona un luogo valido
     *
     * @return Luogo valido
     */
    public String askWhere(Set<String> luoghi) {
        return selectOptionFromCollection(luoghi);
    }

    /**
     * Chiedi l'orario per una proposta
     *
     * @return Orario proposto
     */
    public LocalDateTime askProposedWhen(Set<DayOfWeek> validDaysOfWeek, Set<TimeInterval> validTimeIntervals) {
        LocalTime proposedTime = askTime(validTimeIntervals);
        LocalDate proposedDate = askDate(validDaysOfWeek);
        return LocalDateTime.of(proposedDate, proposedTime);
    }

    /**
     * Chiede conferma dell'appuntamento
     */
    public boolean askAppointmentConfirmation() {
        return inputProvider.yesOrNo(ASK_ACCEPT_APPOINTMENT);
    }

    /**
     * Chiede una data, validata
     *
     * @return Data validata
     */
    public LocalDate askDate(Set<DayOfWeek> validDaysOfWeek) {
        LocalDate proposedDate;
        do {
            int year = inputProvider.leggiIntero(INPUT_YEAR, LocalDate.now().getYear(), LocalDate.now().getYear() + 1);
            int month = inputProvider.leggiIntero(INPUT_MONTH, 1, 12);
            int day = askDay(year, month, validDaysOfWeek);
            proposedDate = LocalDate.of(year, month, day);
            if (!proposedDate.isAfter(LocalDate.now())) {
                System.out.println(ERROR_PAST_DATE);
            }
        } while (!proposedDate.isAfter(LocalDate.now()));
        return proposedDate;
    }

    /**
     * Chiede un giorno del mese, ammesso dall'applicazione, dato l'anno e il mese
     *
     * @param year            Anno
     * @param month           Mese
     * @param validDaysOfWeek giorni validi della settimana per uno scambio
     * @return Giorno del mese, che ricade in un giorno della settimana ammesso dall'applicazione
     */
    public int askDay(int year, int month, Set<DayOfWeek> validDaysOfWeek) {
        Map<Integer, String> validDays = buildValidMonthDays(year, month, validDaysOfWeek);
        validDays.forEach((day, dayOfWeek) -> message("\t" + dayOfWeek + ' ' + day));
        return inputProvider.leggiInteroDaSet(INPUT_DAY, validDays.keySet());
    }

    /**
     * costruisce mappa dei giorni validi per uno scambio
     *
     * @param year anno selezionato dall'utente
     * @param month mese selezionato dall'utente
     * @param validWeekDays giorni della settimana validi per uno scambio
     * @return giorni validi in quel mese di quell'anno
     */
    private Map<Integer, String> buildValidMonthDays(int year, int month, Set<DayOfWeek> validWeekDays) {
        Map<Integer, String> validMonthDays = new TreeMap<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        for (int day = 1; day < yearMonth.lengthOfMonth(); day++) {
            DayOfWeek dayOfWeek = yearMonth.atDay(day).getDayOfWeek();
            if (validWeekDays.contains(dayOfWeek)) {
                validMonthDays.put(day, dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
            }
        }
        return validMonthDays;
    }

    /**
     * Chiedi un orario per il baratto
     *
     * @return Orario per il baratto, validato
     */
    public LocalTime askTime(Set<TimeInterval> timeIntervals) {
        LocalTime proposedTime;
        do {
            int hour = inputProvider.leggiIntero(INPUT_HOUR, 0, 23);
            int minute = inputProvider.leggiInteroDaSet(INPUT_MINUTE, TimeInterval.allowedMinutes());
            proposedTime = LocalTime.of(hour, minute);
            if (!isValidTime(proposedTime, timeIntervals)) {
                message(ERROR_INVALID_TIME);
                timeIntervals.forEach(timeInterval -> System.out.println(timeInterval.allowedTimes()));
            }
        } while (!isValidTime(proposedTime, timeIntervals));
        return proposedTime;
    }

    /**
     * Controlla se l'orario passato è valido rispetto agli intervalli temporali dell'applicazione
     * @param proposedTime Tempo da controllare
     * @param timeIntervals intervalli temporali validi
     * @return 'true' se è valido, 'false' altrimenti
     */
    public boolean isValidTime(LocalTime proposedTime, Set<TimeInterval> timeIntervals) {
        return timeIntervals
                .stream()
                .anyMatch(timeInterval -> timeInterval.isAllowed(proposedTime));
    }
}
