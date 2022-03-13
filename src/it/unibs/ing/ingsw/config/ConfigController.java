package it.unibs.ing.ingsw.config;

import java.time.DayOfWeek;
import java.util.*;

public class ConfigController {
    private Config config;

    public ConfigController(Config config) {
        this.config = config;
    }

    public String getConfigAsString(){
        return config.toString();
    }

    public void makeConfig(String piazza, List<String> luoghi, List<DayOfWeek> days, List<TimeInterval> timeIntervals, int deadline) {
        config = new Config(piazza, luoghi, days, timeIntervals, deadline);
    }

    public List<DayOfWeek> getDays() {
        return config.getDays();
    }

    public Set<int> allowedMinutes() {
        Set<int> minsAllowed = new HashSet<int>();
        minsAllowed.add(0);
        minsAllowed.add(30);
        return minsAllowed;
    }
}
