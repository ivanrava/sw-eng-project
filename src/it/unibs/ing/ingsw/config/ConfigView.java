package it.unibs.ing.ingsw.config;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class ConfigView extends AbstractView {
    public static final String INSERT_PLACE = "Inserisci un luogo di scambio: ";
    public static final String INSERT_PLACE_ANOTHER = "Vuoi inserire un altro luogo? ";
    public static final String ERROR_PLACE_DUPLICATE = "Luogo già presente :(";
    public static final String INSERT_DAY_ANOTHER = "Vuoi inserire un altro giorno? ";
    public static final String ERROR_DAY_DUPLICATE = "Giorno già presente :(";
    public static final String INSERT_TIME_ANOTHER = "Vuoi inserire un altro intervallo? ";
    public static final String INSERT_TIME_START_HOUR = "Inserisci l'ora iniziale: ";
    public static final String INSERT_TIME_START_MINUTES = "Inserisci il minuto iniziale: ";
    public static final String ERROR_TIME_OVERLAP = "C'è una sovrapposizione con un altro orario :(";
    public static final String INSERT_TIME_STOP_HOUR = "Inserisci l'ora finale: ";
    public static final String INSERT_TIME_STOP_MINUTES = "Inserisci il minuto finale: ";
    public static final String MESSAGE_INSERT_DAY_LESS_THAN = ">>> Inserisci un orario <= di %s";
    public static final int MAX_HOUR = 23;
    public static final int MIN_HOUR = 0;
    public static final int MAX_DAYS = 7;
    public static final int MIN_DAYS = 1;
    public static final String INSERT_DAY = String.format("Inserisci un giorno della settimana [%d-%d]: ", MIN_DAYS, MAX_DAYS);
    public static final int MIN_DEADLINE = 1;
    public static final String INSERT_PIAZZA = "Inserisci piazza di scambio definitiva: ";
    public static final String INSERT_DEADLINE = "Inserisci la deadline: ";
    public static final String INSERT_ABSOLUTE_PATH = "Inserisci il percorso assoluto del file: ";
    public static final String INSERT_TIME_INTERVALS = "Inserimento intervalli temporali";
    protected String MENU_TITLE = "Configurazione";

    /**
     * Stampa la configurazione
     */
    public void printConfig(Config config) {
        message(render(config));
    }


    /**
     * Inserisce la piazza
     */
    public String askSquare() {
        return InputDati.leggiStringaNonVuota(INSERT_PIAZZA);
    }

    /**
     * Inserisce la scadenza
     */
    public int askDeadline() {
        return InputDati.leggiInteroConMinimo(INSERT_DEADLINE, MIN_DEADLINE);
    }

    /**
     * Inserisce dall'UI i luoghi della configurazione
     */
    public Set<String> askPlaces() {
        Set<String> places = new HashSet<>();
        boolean continua = true;
        do {
            String place = InputDati.leggiStringaNonVuota(INSERT_PLACE);
            if (!places.contains(place)) {
                places.add(place);
                continua = InputDati.yesOrNo(INSERT_PLACE_ANOTHER);
            } else {
                message(ERROR_PLACE_DUPLICATE);
            }
        } while (continua);
        return places;
    }

    /**
     * Inserisce dall'UI i giorni della configurazione
     */
    public Set<DayOfWeek> askDays() {
        Set<DayOfWeek> days = new TreeSet<>();
        boolean continua = true;
        do {
            DayOfWeek day = DayOfWeek.of(InputDati.leggiIntero(INSERT_DAY, MIN_DAYS, MAX_DAYS));
            if (!days.contains(day)) {
                days.add(day);
                continua = InputDati.yesOrNo(INSERT_DAY_ANOTHER);
            } else {
                message(ERROR_DAY_DUPLICATE);
            }
        } while (continua);
        return days;
    }

    /**
     * Chiede all'UI in loop gli intervalli orari
     */
    public Set<TimeInterval> askTimeIntervals() {
        Set<TimeInterval> timeIntervals = new TreeSet<>();
        message(INSERT_TIME_INTERVALS);
        boolean continua;
        do {
            LocalTime startTime = askStartTime(timeIntervals);
            LocalTime stopTime = askStopTime(startTime, timeIntervals);
            timeIntervals.add(new TimeInterval(startTime, stopTime));
            continua = InputDati.yesOrNo(INSERT_TIME_ANOTHER);
        } while (continua);
        return timeIntervals;
    }

    /**
     * Chiedi l'orario iniziale di un intervallo temporale
     *
     * @return L'orario iniziale di quell'intervallo
     */
    public LocalTime askStartTime(Set<TimeInterval> alreadyInserted) {
        int oraIniziale, minutoIniziale;
        do {
            oraIniziale = InputDati.leggiIntero(INSERT_TIME_START_HOUR, MIN_HOUR, MAX_HOUR);
            minutoIniziale = InputDati.leggiInteroDaSet(INSERT_TIME_START_MINUTES, TimeInterval.allowedMinutes());
            if (isValidStart(oraIniziale, minutoIniziale, alreadyInserted)) {
                message(ERROR_TIME_OVERLAP);
            }
        } while (isValidStart(oraIniziale, minutoIniziale, alreadyInserted));

        return LocalTime.of(oraIniziale, minutoIniziale);
    }

    /**
     * Chiedi l'orario finale di un intervallo temporale
     *
     * @param startTime Il tempo iniziale di quell'intervallo
     * @return L'orario finale di quell'intervallo
     */
    public LocalTime askStopTime(LocalTime startTime, Set<TimeInterval> timeIntervals) {
        LocalTime stopLimit = getStopLimitFor(startTime, timeIntervals);
        int oraFinale, minutoFinale;
        do {
            oraFinale = InputDati.leggiIntero(INSERT_TIME_STOP_HOUR, startTime.getHour(), MAX_HOUR);
            minutoFinale = InputDati.leggiInteroDaSet(INSERT_TIME_STOP_MINUTES, TimeInterval.allowedMinutes());
            if (LocalTime.of(oraFinale, minutoFinale).isAfter(stopLimit)) {
                message(ERROR_TIME_OVERLAP);
                message(String.format(MESSAGE_INSERT_DAY_LESS_THAN, stopLimit));
            }
        } while (LocalTime.of(oraFinale, minutoFinale).isAfter(stopLimit));

        return LocalTime.of(oraFinale, minutoFinale);
    }

    /**
     * Ritorna il limite orario finale per l'ora iniziale passata.
     * Da usare per la validazione degli intervalli orari
     *
     * @param startTime Orario iniziale per l'intervallo in considerazione
     * @return Il massimo orario finale ammissibile
     */
    public LocalTime getStopLimitFor(LocalTime startTime, Set<TimeInterval> timeIntervals) {
        Optional<TimeInterval> timeInterval = timeIntervals
                .stream()
                .filter(timeInt -> timeInt.getStart().isAfter(startTime))
                .findFirst();
        return timeInterval.isPresent()
                ? timeInterval.get().getStart().minusMinutes(TimeInterval.DELTA_MINUTES)
                : TimeInterval.MAX_STOP;
    }

    /**
     * Controlla se l'orario passato è valido come inizio dell'intervallo
     *
     * @param startHour    Ora iniziale
     * @param startMinutes Minuto iniziale
     * @return 'true' se valido, 'false' se invalido
     */
    public boolean isValidStart(int startHour, int startMinutes, Set<TimeInterval> alreadyInserted) {
        // Non dev'essere contenuto negli altri intervalli
        // Non dev'essere uguale al massimo orario possibile
        LocalTime localTime = LocalTime.of(startHour, startMinutes);
        return alreadyInserted
                .stream()
                .anyMatch(timeInterval -> timeInterval.isAllowed(localTime))
            && !TimeInterval.MAX_STOP.equals(localTime);
    }

    public String askPath() {
        return InputDati.leggiStringaNonVuota(INSERT_ABSOLUTE_PATH);
    }

    public boolean askModify(String fieldDescription) {
        return InputDati.yesOrNo("Vuoi modificare il campo \"" + fieldDescription + "\"? ");
    }
}
