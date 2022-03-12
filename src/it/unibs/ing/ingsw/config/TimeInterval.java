package it.unibs.ing.ingsw.config;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TimeInterval {
    private final LocalTime start;
    private final LocalTime stop;

    /**
     * Costruttore parametrizzato
     * @param startHour Ora di partenza
     * @param startMinutes Minuti di partenza
     * @param stopHour Ora di fine
     * @param stopMinutes Minuti di fine
     */
    public TimeInterval(int startHour, int startMinutes, int stopHour, int stopMinutes) {
        assert startMinutes == 30 || startMinutes == 0 : "I minuti non sono 0 o 30";
        assert stopMinutes == 30 || stopMinutes == 0 : "I minuti non sono 0 o 30";
        assert startHour < 24 && startHour >= 0 : "Le ore non sono tra 0 e 23";
        assert stopHour < 24 && stopHour >= 0 : "Le ore non sono tra 0 e 23";

        start = LocalTime.of(startHour, startMinutes);
        stop = LocalTime.of(stopHour, stopMinutes);
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
            accumulator = accumulator.plusMinutes(30);
            times.add(accumulator.toString());
        }
        return times;
    }

    @Override
    public String toString() {
        return allowedTimes().toString();
    }
}
