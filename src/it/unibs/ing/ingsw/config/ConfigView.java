package it.unibs.ing.ingsw.config;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.category.Field;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigView<continua, giorni> {
    ConfigController configController;

    public ConfigView(Config configurazione){
        configController = new ConfigController(configurazione);
    }

    public void printConfig(){
        System.out.println(configController.showAllconfigurationToString());
    }

    //public Config addConfig(){
        //if(!configController.existFConfig){
             //return addConfigFirst();
       // } else{
         //  return addConfigAfterFirst(addConfigFirst());
        //}
    //}



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
            return configController.addConfigControllerFirst(piazza,luoghi,giorni,intervalli_orari,deadline);
    }



    /*
    public Config addConfigAfterFirst(Config oldConfig){  //FIXME : VADO A SOVRASCRIVERE TUTTI I CAMPI TRANNE PIAZZA (MAGARI SI POTREBBE IMPLEMENTARE AGGIUNTA E NON SOVRASCRIZIONE)
        oldConfig = addConfigFirst();
        List<String> luoghi;
        List<Day> giorni;
        List<TimeInterval> intervalli_orari;
        int deadline;
        luoghi = inserisciLuoghi();
        giorni = inserisciGiorni();
        intervalli_orari = inserisciIntervalliOrari();
        deadline = InputDati.leggiInteroConMinimo("inserisci la deadline: ", 0);
        return configController.addConfigControllerFirst(oldConfig.getPiazza(),luoghi,giorni,intervalli_orari,deadline);
    }
    */




    private List<String> inserisciLuoghi() { //TODO
        List<String> luoghi = new ArrayList<>();
        String luogo;
        boolean exist = false ;
        boolean continua;
        do {
            luogo = InputDati.leggiStringaNonVuota("inserisci luogo di scambio:");
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
            continua=InputDati.yesOrNo("vuoi inserire un altro luogo?");
        }while(continua);
        return luoghi;
    }

    private List<Day> inserisciGiorni(){ //FIXME CONTROLLO SU VALUE OF
        List<Day> giorni = new ArrayList<>();
        String giorno;
        boolean exist = false;
        boolean illegalFormat = false;
        boolean continua ;
        Day newDay = null;
        do{
          do {
            giorno = InputDati.leggiStringaNonVuota("inserisci giorno della settimana:");
            try {
                newDay = Day.valueOf(giorno);
                illegalFormat = false;
            } catch (IllegalArgumentException e) {
                System.out.println("hai sbagliato formato del giorno");
                illegalFormat = true;
            }
          }while(illegalFormat)   ;

            for(Day oldDay : giorni){
                if(newDay.equals(oldDay)){
                    System.out.println("giorno gia inserito");
                    exist = true;
                }
            }
            if(!exist) {
                giorni.add(newDay);
                System.out.println("giorno inserito correttamente :)");
            }
            continua = InputDati.yesOrNo("vuoi inserire un altro giorno?");
        }while(continua);
        return giorni;

    }


    private List<TimeInterval> inserisciIntervalliOrari(){ //FIXME TROVARE MODO PIU ELEGANTE
        List<TimeInterval> intervalli_orari = new ArrayList<>();
        boolean continua;
        int oraIniziale, oraFinale, minutoIniziale, minutoFinale;
        do{
            TimeInterval intervallo;
            do {
                oraIniziale = InputDati.leggiIntero("inserisci l'ora iniziale :");
            }while(!configController.checkHour(oraIniziale));
            do {
                minutoIniziale = InputDati.leggiIntero("inserisci il minuto iniziale:");
            }while(!configController.checkMinut(minutoIniziale));
            do {
                do {
                    oraFinale = InputDati.leggiIntero("inserisci l'ora finale :");
                }while(!configController.checkHour(oraFinale));
                do {
                    minutoFinale = InputDati.leggiIntero("inserisci il minuto finale:");
                }while(!configController.checkMinut(minutoFinale));
                if((oraFinale == oraIniziale && minutoFinale < minutoIniziale) || (oraFinale < oraIniziale)){
                    System.out.println("hai inserito orario finale minore di quello iniziale");
                }
            } while((oraFinale == oraIniziale && minutoFinale < minutoIniziale) || (oraFinale < oraIniziale) );
            intervallo = new TimeInterval(oraIniziale,minutoIniziale,oraFinale,minutoFinale);
            intervalli_orari.add(intervallo);
            continua = InputDati.yesOrNo("vuoi inserire un altro intervallo?");
        }while(continua);
        return intervalli_orari;
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
