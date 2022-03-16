package it.unibs.ing.ingsw.config;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.fp.mylib.MyMenu;
import it.unibs.ing.ingsw.io.Saves;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class ConfigView {
    public static final int MAX_HOUR = 23;
    public static final int MIN_HOUR = 0;
    public static final int MAX_DAYS = 7;
    public static final int MIN_DAYS = 1;
    public static final int MIN_DEADLINE = 1;
    ConfigController configController;

    public ConfigView(Saves saves){
        configController = new ConfigController(saves);
    }

    /**
     * Esegui l'UI di gestione della configurazione
     */
    public void execute() {
        MyMenu mainMenu = new MyMenu("Configurazione", new String[] {
                "Visualizza Configurazione",
                "Modifica Configurazione"
        });

        int scelta;
        do {
            scelta = mainMenu.scegli();
            switch (scelta) {
                case 1 -> printConfig();
                case 2 -> editConfig();
            }
        }while (scelta != 0);
    }

    /**
     * Stampa la configurazione
     */
    public void printConfig(){
        System.out.println(configController.getConfigAsString());
    }

    /**
     * Modifica la configurazione
     */
    public void editConfig() {
        if(!configController.existsDefaultValues()) {
            addConfigFirst();
        } else {
            updateConfig();
        }
    }

    /**
     * Crea la prima configurazione
     */
    public void addConfigFirst() {
        inserisciPiazza();
        inserisciLuoghi();
        inserisciGiorni();
        inserisciIntervalliOrari();
        inserisciDeadline();
    }

    /**
     * Aggiorna la configurazione già esistente
     */
    public void updateConfig() {
        if (InputDati.yesOrNo("Vuoi modificare i luoghi inseriti? "))
            inserisciLuoghi();
        if (InputDati.yesOrNo("Vuoi modificare i giorni già inseriti? "))
            inserisciGiorni();
        if (InputDati.yesOrNo("Vuoi modificare gli intervalli orari già inseriti? "))
            inserisciIntervalliOrari();
        if (InputDati.yesOrNo("Vuoi modificare la scadenza predefinita di baratto? "))
            inserisciDeadline();
    }

    /**
     * Inserisce la piazza
     */
    private void inserisciPiazza() {
        configController.setPiazza(InputDati.leggiStringaNonVuota("Inserisci piazza di scambio definitiva: "));
    }

    /**
     * Inserisce la scadenza
     */
    private void inserisciDeadline() {
        configController.setDeadLine(InputDati.leggiInteroConMinimo("Inserisci la deadline: ", MIN_DEADLINE));
    }

    /**
     * Inserisce dall'UI i luoghi della configurazione
     */
    private void inserisciLuoghi() {
        boolean continua = true;
        do {
            String luogo = InputDati.leggiStringaNonVuota("Inserisci un luogo di scambio: ");
            if (!configController.exists(luogo)) {
                configController.addLuogo(luogo);
                continua = InputDati.yesOrNo("Vuoi inserire un altro luogo? ");
            } else {
                System.out.println("Luogo già presente :(");
            }
        } while(continua);
    }

    /**
     * Stampa i giorni della configurazione
     */
    private void printDays() {
        configController.getDays().forEach(
                dayOfWeek -> System.out.printf("%s ", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ITALIAN))
        );
    }

    /**
     * Inserisce dall'UI i giorni della configurazione
     */
    private void inserisciGiorni() {
        printDays();

        boolean continua = true;
        do {
            DayOfWeek day = DayOfWeek.of(InputDati.leggiIntero(
                    String.format("Inserisci un giorno della settimana [%d-%d]: ", MIN_DAYS, MAX_DAYS),
                    MIN_DAYS, MAX_DAYS));
            if (!configController.exists(day)) {
                configController.addDay(day);
                continua = InputDati.yesOrNo("Vuoi inserire un altro giorno? ");
            } else {
                System.out.println("Giorno già presente :(");
            }
        } while(continua);
    }

    /**
     * Chiede all'UI in loop gli intervalli orari
     */
    private void inserisciIntervalliOrari() {
        boolean continua;
        do {
            LocalTime startTime = askStartTime();
            LocalTime stopTime = askStopTime(startTime);
            configController.addTimeInterval(startTime, stopTime);
            continua = InputDati.yesOrNo("Vuoi inserire un altro intervallo? ");
        } while(continua);
    }

    /**
     * Chiedi l'orario iniziale di un intervallo temporale
     * @return L'orario iniziale di quell'intervallo
     */
    private LocalTime askStartTime() {
        int oraIniziale, minutoIniziale;
        do {
            oraIniziale = InputDati.leggiIntero("Inserisci l'ora iniziale: ", MIN_HOUR, MAX_HOUR);
            minutoIniziale = InputDati.leggiInteroDaSet("Inserisci il minuto iniziale: ", configController.allowedMinutes());
            if (!configController.isValidStart(oraIniziale, minutoIniziale)) {
                System.out.println("C'è una sovrapposizione con un altro orario :(");
            }
        } while (!configController.isValidStart(oraIniziale, minutoIniziale));

        return LocalTime.of(oraIniziale, minutoIniziale);
    }

    /**
     * Chiedi l'orario finale di un intervallo temporale
     * @param startTime Il tempo iniziale di quell'intervallo
     * @return L'orario finale di quell'intervallo
     */
    private LocalTime askStopTime(LocalTime startTime) {
        LocalTime stopLimit = configController.getStopLimitFor(startTime);
        int oraFinale, minutoFinale;
        do {
            oraFinale = InputDati.leggiIntero("Inserisci l'ora finale: ", startTime.getHour(), MAX_HOUR);
            minutoFinale = InputDati.leggiInteroDaSet("Inserisci il minuto finale: ", configController.allowedMinutes());
            if (!LocalTime.of(oraFinale, minutoFinale).isBefore(stopLimit)) {
                System.out.println("C'è una sovrapposizione con un altro orario :(");
                System.out.printf(">>> Inserisci un orario <= di %s", stopLimit);
            }
        } while (!LocalTime.of(oraFinale, minutoFinale).isBefore(stopLimit));

        return LocalTime.of(oraFinale, minutoFinale);
    }
}
