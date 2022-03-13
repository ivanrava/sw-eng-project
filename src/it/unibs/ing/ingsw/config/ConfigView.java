package it.unibs.ing.ingsw.config;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.Field;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigView {
    ConfigController configController;


    public ConfigView(Config configurazione){
        configController = new ConfigController(configurazione);
    }

    public void printConfig(){
        System.out.println(configController.showAllconfigurationToString());
    }

    public Config addConfig(){  //FIXME TUTTI I CONTROLLI : DEMO VERSION
        String piazza;
        List<String> luoghi;
        List<Day> giorni;
        List<TimeInterval> intervalli_orari;
        int deadline;
            piazza = InputDati.leggiStringaNonVuota("Inserisci piazza di scambio definitiva: ");
            luoghi = inserisciLuoghi();
            giorni = inserisciGiorni();
            intervalli_orari = inserisciIntervalliOrari();
            deadline = InputDati.leggiInteroConMinimo("inserisci la deadline: ", 0);
            configController.addConfigController(piazza,luoghi,giorni,intervalli_orari,deadline);
    }

    private List<String> inserisciLuoghi() { //TODO
        List<String> luoghi = new ArrayList<>();
        String luogo;
        boolean exist ;
        do {
            luogo = InputDati.leggiStringaNonVuota("inserisci luogo di scambio (inserire stringa fine per terminare) :");
            for(String luogoOld : luoghi){
                if(luogoOld.equals(luogo)){
                    System.out.println("luogo gia inserito");
                    exist = true;
                }
            }
            if(!exist) {
                luoghi.add(luogo);
                System.out.println("luogo inserito correttamente :)");
            }
        }while(!luogo.equals("fine"));
        return luoghi;
    }

    private List<Day> inserisciGiorni(){ //TODO

    }

    private List<TimeInterval> inserisciIntervalliOrari(){ //TODO

    }




    public void modifyConfig(){ //FIXME  : MEGLIO AGGIUNGERLO IN CONFIGURATOR VIEW ?!
        MyMenu mainMenu = new MyMenu("Configurazione", new String[] {
                "Visualizza Configurazione",
                "Modifica Configurazione"
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> printConfig();
                // TODO: case 2
                case 2 -> addConfig();
            }
        }while (scelta != 0);
    }


}
