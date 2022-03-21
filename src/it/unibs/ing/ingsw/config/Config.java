package it.unibs.ing.ingsw.config;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class Config implements Serializable {
    private boolean isConfigured = false;
    private String piazza;
    private final Set<String> luoghi;
    private final Set<DayOfWeek> days;
    private final Set<TimeInterval> timeIntervals;
    private int deadline;

    public Config(String piazza, Set<String> luoghi, Set<DayOfWeek> days, Set<TimeInterval> timeIntervals, int deadline) {
        this.piazza = piazza;
        this.luoghi = luoghi;
        this.days = days;
        this.timeIntervals = timeIntervals;
        this.deadline = deadline;
    }

    public Set<String> getLuoghi() {
        return luoghi;
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public void setDeadLine (int deadline){
        this.deadline = deadline;
    }

    public Set<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    @Override
    public String toString() {
        return "Config{" +
                "piazza='" + piazza + '\'' +
                ", luoghi=" + luoghi +
                ", giorni=" + days +
                ", intervalli orari=" + timeIntervals +
                ", scadenza=" + deadline +
                '}';
    }

    /**
     * Informa se la config è già stata configurata
     * @return 'true' se già configurata, 'false' altrimenti
     */
    public boolean isConfigured() {
        return isConfigured;
    }

    /**
     * Imposta i valori immutabili della config.
     *
     * Chiamate di questo metodo con valori già configurati (controllabile con isConfigured())
     * ritorneranno un'eccezione di tipo IllegalArgumentException.
     * @param piazza Piazza in cui si effettuano gli scambi
     */
    public void setImmutableValues(String piazza) {
        if (isConfigured) {
            throw new IllegalArgumentException("I valori obbligatori della Config sono già stati impostati");
        } else {
            this.piazza = piazza;
            this.isConfigured = true;
        }
    }

    public void addLuogo(String luogo) {
        luoghi.add(luogo);
    }

    public void addDay(DayOfWeek day) {
        days.add(day);
    }

    public void addTimeInterval(LocalTime start, LocalTime stop) {
        timeIntervals.add(new TimeInterval(start, stop));
    }

    /**
     * Dice se gli intervalli temporali contengono l'orario passato
     * @param hour Ora da considerare
     * @param minutes Minuti da considerare
     * @return 'true' se è contenuto, 'false' altrimenti
     */
    public boolean timeIntervalsContain(int hour, int minutes) {
        for (TimeInterval timeInterval: timeIntervals) {
            if (timeInterval.contains(LocalTime.of(hour, minutes))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ritorna il massimo orario finale sulla base dell'orario iniziale specificato
     * @param startTime Orario iniziale da considerare
     * @return Massimo orario finale ammesso per quell'orario iniziale
     */
    public LocalTime getMaximumStopAfter(LocalTime startTime) {
        for (TimeInterval timeInterval: timeIntervals) {
            if (timeInterval.getStart().isAfter(startTime)) {
                return timeInterval.getStart().minusMinutes(TimeInterval.DELTA_MINUTES);
            }
        }
        return TimeInterval.MAX_STOP;
    }

    /**
     * Controlla se l'orario passato è uguale all'orario massimo concesso dall'applicazione
     * @param startHour Ore iniziali
     * @param startMinutes Minuti iniziali
     * @return 'true' se uguale, 'false' altrimenti
     */
    public boolean isMaxTime(int startHour, int startMinutes) {
        return TimeInterval.MAX_STOP.equals(LocalTime.of(startHour, startMinutes));
    }


}
