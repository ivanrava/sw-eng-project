package it.unibs.ing.ingsw.config;

import it.unibs.ing.ingsw.io.Saves;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigController {

    private Config configurazione;
   // private final Saves saves ;


    public ConfigController(Config configurazione) {

        this.configurazione = configurazione;
    }

    public String showAllconfigurationToString(){
        return configurazione.toString();
    }

    public Config addConfigControllerFirst(String piazza, List<String> luoghi, List<DayOfWeek> giorni, List<TimeInterval> intervalli_orari, int deadline){
        return configurazione = new Config(piazza, luoghi, giorni, intervalli_orari, deadline);
    }

    public List<DayOfWeek> getDays() {
        return configurazione.getDays();
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
