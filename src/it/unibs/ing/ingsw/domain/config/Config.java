package it.unibs.ing.ingsw.domain.config;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Objects;
import java.util.Set;

public class Config implements Serializable {
    private String username;
    private String password;
    private String square;
    private Set<String> places;
    private Set<DayOfWeek> days;
    private Set<TimeInterval> timeIntervals;
    private int deadline;

    public Config(String square, Set<String> places, Set<DayOfWeek> days, Set<TimeInterval> timeIntervals, int deadline) {
        this.square = square;
        this.places = places;
        this.days = days;
        this.timeIntervals = timeIntervals;
        this.deadline = deadline;
    }

    public Config(Set<String> places, Set<DayOfWeek> days, Set<TimeInterval> timeIntervals, int deadline) {
        this(null, places, days, timeIntervals, deadline);
    }

    public Set<String> getPlaces() {
        return places;
    }

    public void setPlaces(Set<String> places) {
        this.places = places;
    }

    public String getSquare() {
        return square;
    }

    public Set<DayOfWeek> getDays() {
        return days;
    }

    public void setDays(Set<DayOfWeek> days) {
        this.days = days;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getDeadline() {
        return deadline;
    }

    public Set<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    public void setTimeIntervals(Set<TimeInterval> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }

    /**
     * Informa se la config è già stata configurata
     *
     * @return 'true' se già configurata, 'false' altrimenti
     */
    public boolean isConfiguredImmutableValues() {
        return square != null;
    }

    /**
     * Imposta i valori immutabili della config.
     * <p>
     * Chiamate di questo metodo con valori già configurati (controllabile con isConfigured())
     * ritorneranno un'eccezione di tipo IllegalArgumentException.
     *
     * @param piazza Piazza in cui si effettuano gli scambi
     */
    public void setImmutableValues(String piazza) {
        if (isConfiguredImmutableValues())
            throw new IllegalArgumentException("I valori obbligatori della Config sono già stati impostati");
        else this.square = piazza;
    }

    /**
     * Imposta i valori immutabili di configurazione
     *
     * @param username Username di default
     * @param password Password di default
     */
    public void setDefaultCredentials(String username, String password) {
        if (isConfiguredDefaultCredentials()) {
            throw new IllegalArgumentException("I valori obbligatori della Config sono già stati impostati");
        } else {
            this.username = username;
            this.password = password;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return 'true' se sono configurate le credenziali di default, 'false' altrimenti
     */
    public boolean isConfiguredDefaultCredentials() {
        return username != null && password != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config config = (Config) o;
        return deadline == config.deadline && Objects.equals(username, config.username) && Objects.equals(password, config.password) && Objects.equals(square, config.square) && Objects.equals(places, config.places) && Objects.equals(days, config.days) && Objects.equals(timeIntervals, config.timeIntervals);
    }
}
