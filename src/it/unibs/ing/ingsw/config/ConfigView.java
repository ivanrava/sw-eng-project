package it.unibs.ing.ingsw.config;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConfigView {
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
        List<DayOfWeek> giorni;
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

    /**
     * Inserisce dall'UI una lista di luoghi
     * @return Una lista di luoghi
     */
    private List<String> inserisciLuoghi() {
        List<String> luoghi = new ArrayList<>();
        boolean continua = true;
        do {
            String luogo = InputDati.leggiStringaNonVuota("Inserisci luogo di scambio: ");
            if (luoghi.contains(luogo)) {
                System.out.println("Luogo già presente :(");
            } else {
                luoghi.add(luogo);
                continua = InputDati.yesOrNo("Vuoi inserire un altro luogo? ");
            }
        } while(continua || luoghi.isEmpty());

        return luoghi;
    }

    /**
     * Stampa i giorni della configurazione
     */
    private void printDays() {
        configController.getDays().forEach(dayOfWeek -> {
            System.out.printf("%s ", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ITALIAN));
        });
    }

    /**
     * Inserisce dall'UI una lista di giorni
     * @return Una lista di giorni
     */
    private List<DayOfWeek> inserisciGiorni() {
        printDays();

        List<DayOfWeek> days = new ArrayList<>();
        boolean continua = true;
        do {
            DayOfWeek day = DayOfWeek.of(InputDati.leggiIntero("Inserisci un giorno della settimana [1-7]: ", 1, 7));
            if (days.contains(day)) {
                System.out.println("Giorno già presente :(");
            } else {
                days.add(day);
                continua = InputDati.yesOrNo("Vuoi inserire un altro giorno? ");
            }
        } while(continua || days.isEmpty());

        return days;
    }

    private List<TimeInterval> inserisciIntervalliOrari() {
        // TODO: Mancano controlli per intervalli sovrapposti nella lista
        List<TimeInterval> timeIntervals = new ArrayList<>();
        boolean continua;
        int oraIniziale, oraFinale, minutoIniziale, minutoFinale;
        do{
            oraIniziale = InputDati.leggiIntero("Inserisci l'ora iniziale: ", 0, 23);
            minutoIniziale = InputDati.leggiInteroDaSet("Inserisci il minuto iniziale: ", configController.allowedMinutes());
            oraFinale = InputDati.leggiIntero("Inserisci l'ora finale: ", oraIniziale, 23);
            do {
                minutoFinale = InputDati.leggiInteroDaSet("Inserisci il minuto finale: ", configController.allowedMinutes());
                if (minutoIniziale > minutoFinale) {
                    System.out.println("Orario finale < orario iniziale :(");
                }
            } while (minutoIniziale > minutoFinale);
            timeIntervals.add(new TimeInterval(oraIniziale, minutoIniziale, oraFinale, minutoFinale));
            continua = InputDati.yesOrNo("Vuoi inserire un altro intervallo? ");
        } while(continua);
        return timeIntervals;
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
                case 2 -> addConfig();
            }
        }while (scelta != 0);
    }




}
