package it.unibs.ing.ingsw.config;

import it.unibs.ing.ingsw.io.Saves;

import java.util.List;

public class ConfigController {

    private final Config configurazione;
   // private final Saves saves ;


    public ConfigController(Config configurazione) {

        this.configurazione = configurazione;
    }

    public String showAllconfigurationToString(){
        return configurazione.toString();
    }

    public Config addConfigControllerFirst(String piazza, List<String> luoghi, List<Day> giorni, List<TimeInterval> intervalli_orari, int deadline){
        return new Config(piazza, luoghi, giorni, intervalli_orari, deadline);
    }

    public boolean checkHour(int hour){
        return hour >= 0 && hour < 24;
    }


    public boolean checkMinut (int minut){
        return minut == 0 || minut ==30;
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
