package it.unibs.ing.ingsw.config;

import java.util.List;

public class ConfigController {

    private Config configurazione;


    public ConfigController(Config configurazione) {
        this.configurazione = configurazione;
    }

    public String showAllconfigurationToString(){
        return configurazione.toString();
    }

    public Config addConfigController(String piazza, List<String> luoghi, List<Day> giorni, List<TimeInterval> intervalli_orari, int deadline){
        return configurazione = new Config(piazza, luoghi, giorni, intervalli_orari, deadline);
    }





}
