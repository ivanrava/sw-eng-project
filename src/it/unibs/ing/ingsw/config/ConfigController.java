package it.unibs.ing.ingsw.config;

import java.time.DayOfWeek;
import java.util.*;
import it.unibs.ing.ingsw.io.Saves;

import java.util.List;

public class ConfigController {
    private final Config config;
   // private final Saves saves ;


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


    public Config getConfigurazione() {
        return configurazione;
    }

    /*
    public boolean existFConfig(){
        return saves.existsFileConfiguration();
    }
    */   //per verificare che ci sia all'avvio un file di configurazione degli appuntamenti oppure no
}
