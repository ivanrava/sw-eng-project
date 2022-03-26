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
    public static final String INSERT_DAY = String.format("Inserisci un giorno della settimana [%d-%d]: ", MIN_DAYS, MAX_DAYS);
    public static final int MIN_DEADLINE = 1;
    public static final String MENU_TITLE = "Configurazione";
    public static final String[] VOCI = {
            "Visualizza Configurazione",
            "Modifica Configurazione"
    };
    public static final String EDIT_PLACES = "Vuoi modificare i luoghi inseriti? ";
    public static final String EDIT_DAYS = "Vuoi modificare i giorni già inseriti? ";
    public static final String EDIT_TIMES = "Vuoi modificare gli intervalli orari già inseriti? ";
    public static final String EDIT_DEADLINE = "Vuoi modificare la scadenza predefinita di baratto? ";
    public static final String INSERT_PIAZZA = "Inserisci piazza di scambio definitiva: ";
    public static final String INSERT_DEADLINE = "Inserisci la deadline: ";
    public static final String INSERT_PLACE = "Inserisci un luogo di scambio: ";
    public static final String INSERT_PLACE_ANOTHER = "Vuoi inserire un altro luogo? ";
    public static final String ERROR_PLACE_DUPLICATE = "Luogo già presente :(";
    public static final String INSERT_DAY_ANOTHER = "Vuoi inserire un altro giorno? ";
    public static final String ERROR_DAY_DUPLICATE = "Giorno già presente :(";
    public static final String INSERT_TIME_ANOTHER = "Vuoi inserire un altro intervallo? ";
    public static final String INSERT_TIME_START_HOUR = "Inserisci l'ora iniziale: ";
    public static final String INSERT_TIME_START_MINUTES = "Inserisci il minuto iniziale: ";
    public static final String ERROR_TIME_OVERLAP = "C'è una sovrapposizione con un altro orario :(";
    public static final String INSERT_TIME_STOP_HOUR = "Inserisci l'ora finale: ";
    public static final String INSERT_TIME_STOP_MINUTES = "Inserisci il minuto finale: ";
    public static final String MESSAGE_INSERT_DAY_LESS_THAN = ">>> Inserisci un orario <= di %s";
    ConfigController configController;

    public ConfigView(Saves saves){
        configController = new ConfigController(saves);
    }

    /**
     * Esegui l'UI di gestione della configurazione
     */
    public void execute() {
        MyMenu mainMenu = new MyMenu(MENU_TITLE, VOCI);

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
        if (InputDati.yesOrNo(EDIT_PLACES))
            inserisciLuoghi();
        if (InputDati.yesOrNo(EDIT_DAYS))
            inserisciGiorni();
        if (InputDati.yesOrNo(EDIT_TIMES))
            inserisciIntervalliOrari();
        if (InputDati.yesOrNo(EDIT_DEADLINE))
            inserisciDeadline();
    }

    /**
     * Inserisce la piazza
     */
    private void inserisciPiazza() {
        configController.setPiazza(InputDati.leggiStringaNonVuota(INSERT_PIAZZA));
    }

    /**
     * Inserisce la scadenza
     */
    private void inserisciDeadline() {
        configController.setDeadline(InputDati.leggiInteroConMinimo(INSERT_DEADLINE, MIN_DEADLINE));
    }

    /**
     * Inserisce dall'UI i luoghi della configurazione
     */
    private void inserisciLuoghi() {
        boolean continua = true;
        do {
            String luogo = InputDati.leggiStringaNonVuota(INSERT_PLACE);
            if (!configController.exists(luogo)) {
                configController.addLuogo(luogo);
                continua = InputDati.yesOrNo(INSERT_PLACE_ANOTHER);
            } else {
                System.out.println(ERROR_PLACE_DUPLICATE);
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
            DayOfWeek day = DayOfWeek.of(InputDati.leggiIntero(INSERT_DAY, MIN_DAYS, MAX_DAYS));
            if (!configController.exists(day)) {
                configController.addDay(day);
                continua = InputDati.yesOrNo(INSERT_DAY_ANOTHER);
            } else {
                System.out.println(ERROR_DAY_DUPLICATE);
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
            continua = InputDati.yesOrNo(INSERT_TIME_ANOTHER);
        } while(continua);
    }

    /**
     * Chiedi l'orario iniziale di un intervallo temporale
     * @return L'orario iniziale di quell'intervallo
     */
    private LocalTime askStartTime() {
        int oraIniziale, minutoIniziale;
        do {
            oraIniziale = InputDati.leggiIntero(INSERT_TIME_START_HOUR, MIN_HOUR, MAX_HOUR);
            minutoIniziale = InputDati.leggiInteroDaSet(INSERT_TIME_START_MINUTES, configController.allowedMinutes());
            if (!configController.isValidStart(oraIniziale, minutoIniziale)) {
                System.out.println(ERROR_TIME_OVERLAP);
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
            oraFinale = InputDati.leggiIntero(INSERT_TIME_STOP_HOUR, startTime.getHour(), MAX_HOUR);
            minutoFinale = InputDati.leggiInteroDaSet(INSERT_TIME_STOP_MINUTES, configController.allowedMinutes());
            if (LocalTime.of(oraFinale, minutoFinale).isAfter(stopLimit)) {
                System.out.println(ERROR_TIME_OVERLAP);
                System.out.printf(MESSAGE_INSERT_DAY_LESS_THAN, stopLimit);
            }
        } while (LocalTime.of(oraFinale, minutoFinale).isAfter(stopLimit));

        return LocalTime.of(oraFinale, minutoFinale);
    }
}
