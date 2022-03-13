package it.unibs.ing.ingsw.config;

import java.io.Serializable;
import java.sql.Time;
import java.util.List;

public class Config implements Serializable{
    private final String piazza;
    private List<String> luoghi;
    private List<Day> giorni;
    private List<TimeInterval> intervalli_orari;
    private int deadline;


    public Config(String piazza, List<String> luoghi, List<Day> giorni, List<TimeInterval> intervalli_orari, int deadline) {
        this.piazza = piazza;
        this.luoghi = luoghi;
        this.giorni = giorni;
        this.intervalli_orari = intervalli_orari;
        this.deadline = deadline;
    }



    public String getPiazza() {
        return piazza;
    }



    public List<String> getLuoghi() {
        return luoghi;
    }


    public List<Day> getGiorni() {
        return giorni;
    }


    public List<TimeInterval> getIntervalli_orari() {
        return intervalli_orari;
    }


    public int getDeadline() {
        return deadline;
    }




    @Override
    public String toString() {
        return "Config{" +
                "piazza='" + piazza + '\'' +
                ", luoghi=" + luoghi +
                ", giorni=" + giorni +
                ", intervalli_orari=" + intervalli_orari +
                ", deadline=" + deadline +
                '}';
    }




}
