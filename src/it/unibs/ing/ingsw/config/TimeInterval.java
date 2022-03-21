package it.unibs.ing.ingsw.config;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TimeInterval implements Comparable<TimeInterval>, Serializable {
    public static final String ASSERTION_MINUTES = "I minuti non sono 0 o 30";
    public static final String ASSERTION_HOURS = "Le ore non sono tra 0 e 23";
    private final LocalTime start;
    private final LocalTime stop;
    public static final LocalTime MAX_STOP = LocalTime.of(23, 30);
    public static final int DELTA_MINUTES = 30;

    /**
     * Costruttore parametrizzato
     * @param startHour Ora di partenza
     * @param startMinutes Minuti di partenza
     * @param stopHour Ora di fine
     * @param stopMinutes Minuti di fine
     */
    public TimeInterval(int startHour, int startMinutes, int stopHour, int stopMinutes) {
        assert allowedMinutes().contains(startMinutes) : ASSERTION_MINUTES;
        assert allowedMinutes().contains(stopMinutes) : ASSERTION_MINUTES;
        assert startHour < 24 && startHour >= 0 : ASSERTION_HOURS;
        assert stopHour < 24 && stopHour >= 0 : ASSERTION_HOURS;

        start = LocalTime.of(startHour, startMinutes);
        stop = LocalTime.of(stopHour, stopMinutes);
    }

    public TimeInterval(LocalTime start, LocalTime stop) {
        assert allowedMinutes().contains(start.getMinute()) : ASSERTION_MINUTES;
        assert allowedMinutes().contains(stop.getMinute()) : ASSERTION_MINUTES;
        this.start = start;
        this.stop = stop;
    }

    /**
     * Computa la lista di tempi di mezz'ora in mezz'ora
     *
     * Un TimeInterval tra 10:30 e 12:00 ritorna:
     *  10:30 11:00 11:30 12:00
     * @return Lista di stringhe rappresentanti gli eventi scanditi di mezz'ora in mezz'ora
     */
    public List<String> allowedTimes() {
        ArrayList<String> times = new ArrayList<>();
        times.add(start.toString());
        LocalTime accumulator = start;
        while(accumulator.isBefore(stop)) {
            accumulator = accumulator.plusMinutes(DELTA_MINUTES);
            times.add(accumulator.toString());
        }
        return times;
    }

    @Override
    public String toString() {
        return allowedTimes().toString();
    }

    public boolean contains(LocalTime check) {
        return check.equals(start) || check.equals(stop) || (check.isAfter(start) && check.isBefore(stop));
    }

    @Override
    public int compareTo(TimeInterval o) {
        if (start.isBefore(o.start)) {
            // Prima
            return -1;
        } else if (start.equals(o.start)) {
            // Uguale
            return 0;
        } else {
            // Dopo
            return 1;
        }
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getStop() {
        return stop;
    }

    /**
     * Ritorna l'insieme di minuti ammessi per un orario
     * @return I minuti ammessi per un orario
     */
    public static Set<Integer> allowedMinutes() {
        Set<Integer> minsAllowed = new HashSet<>();
        minsAllowed.add(0);
        minsAllowed.add(30);
        return minsAllowed;
    }


}
