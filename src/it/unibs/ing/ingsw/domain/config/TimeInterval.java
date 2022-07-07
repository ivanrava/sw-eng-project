package it.unibs.ing.ingsw.domain.config;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.*;

public class TimeInterval implements Comparable<TimeInterval>, Serializable {
    public static final String ASSERTION_MINUTES = "I minuti non sono 0 o 30";
    private final LocalTime start;
    private final LocalTime stop;
    public static final LocalTime MAX_STOP = LocalTime.of(23, 30);
    public static final int DELTA_MINUTES = 30;

    /**
     * Costruttore parametrizzato
     * @param start Orario di partenza dell'intervallo
     * @param stop Orario di fine dell'intervallo
     */
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
     * @return Lista di tempi rappresentanti gli eventi scanditi di mezz'ora in mezz'ora
     */
    public List<LocalTime> allowedTimes() {
        ArrayList<LocalTime> times = new ArrayList<>();
        times.add(start);
        LocalTime accumulator = start;
        while(accumulator.isBefore(stop)) {
            accumulator = accumulator.plusMinutes(DELTA_MINUTES);
            times.add(accumulator);
        }
        return times;
    }

    @Override
    public String toString() {
        return allowedTimes().toString();
    }

    /**
     * Controlla se l'orario passato è ammesso
     * @param check Orario di riferimento
     * @return 'true' se ammesso, 'false' altrimenti
     */
    public boolean isAllowed(LocalTime check) {
        return allowedTimes().contains(check);
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

    /**
     * Ritorna l'insieme di minuti ammessi per un orario
     * @return I minuti ammessi per un orario
     */
    public static Set<Integer> allowedMinutes() {
        return Set.of(0, 30);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeInterval that = (TimeInterval) o;
        return Objects.equals(start, that.start) && Objects.equals(stop, that.stop);
    }
}
