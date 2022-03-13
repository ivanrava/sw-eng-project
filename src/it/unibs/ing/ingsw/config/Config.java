package it.unibs.ing.ingsw.config;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

public class Config implements Serializable {
    private final String piazza;
    private final List<String> luoghi;
    private final List<DayOfWeek> days;
    private final List<TimeInterval> timeIntervals;
    private final int deadline;

    public Config(String piazza, List<String> luoghi, List<DayOfWeek> days, List<TimeInterval> timeIntervals, int deadline) {
        this.piazza = piazza;
        this.luoghi = luoghi;
        this.days = days;
        this.timeIntervals = timeIntervals;
        this.deadline = deadline;
    }

    public String getPiazza() {
        return piazza;
    }

    public List<String> getLuoghi() {
        return luoghi;
    }

    public List<DayOfWeek> getDays() {
        return days;
    }

    public List<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    public int getDeadline() {
        return deadline;
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
}
