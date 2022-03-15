package it.unibs.ing.ingsw.config;

import it.unibs.ing.ingsw.io.Saves;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigController {

    private Config configurazione;
   private final Saves saves ;


    public ConfigController(Saves saves) {
        this.saves = saves;
        this.configurazione = saves.getConfig();
    }

    public boolean existConfig(){
        return saves.existsConfiguration();
    }

    public String getPiazza (){
        return configurazione.getPiazza();
    }

    public List<String> getLuoghi(){
        return configurazione.getLuoghi();
    }




    public String showAllconfigurationToString(){
        return configurazione.toString();
    }

    public Config addConfigControllerFirst(String piazza, List<String> luoghi, List<DayOfWeek> giorni, List<TimeInterval> intervalli_orari, int deadline){ //FIXME
        return configurazione = new Config(piazza,luoghi,giorni,intervalli_orari,deadline);
    }

    public Config addConfigControllerAfterFirst(List<String> luoghi, List<DayOfWeek> giorni, List<TimeInterval> intervalli_orari, int deadline){ //FIXME
        configurazione.getLuoghi().addAll(luoghi);
        configurazione.getDays().addAll(giorni);
        configurazione.getTimeIntervals().addAll(intervalli_orari);
        configurazione.setDeadLine(deadline);
        return configurazione;
    }


    public List<DayOfWeek> getDays() {
        return configurazione.getDays();
    }

    public Set<Integer> allowedMinutes() {
        Set<Integer> minsAllowed = new HashSet<Integer>();
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
