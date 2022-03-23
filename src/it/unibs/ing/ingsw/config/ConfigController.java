package it.unibs.ing.ingsw.config;

import it.unibs.ing.ingsw.io.Saves;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class ConfigController {
    private final Saves saves;

    public ConfigController(Saves saves) {
        this.saves = saves;
    }

    public boolean existsDefaultValues(){
        return saves.existsConfiguration() || saves.getConfig().isConfigured();
    }

    /**
     * Ritorna la configurazione come stringa human-readable
     * @return Configurazione come stringa
     */
    public String getConfigAsString() {
        return saves.getConfig().toString();
    }

    /**
     * Imposta la piazza in cui avvengono gli scambi
     * @param piazza Piazza da impostare
     */
    public void setPiazza(String piazza) {
        saves.getConfig().setImmutableValues(piazza);
    }

    /**
     * Aggiunge un giorno in cui possono avvenire gli scambi
     * @param day Giorno da aggiungere
     */
    public void addDay(DayOfWeek day) {
        saves.getConfig().addDay(day);
    }

    /**
     * Aggiunge un luogo in cui possono avvenire gli scambi
     * @param luogo Luogo da aggiungere
     */
    public void addLuogo(String luogo) {
        saves.getConfig().addLuogo(luogo);
    }

    /**
     * Aggiunge un intervallo temporale in cui possono avvenire gli scambi
     * @param start Orario iniziale dell'intervallo
     * @param stop Orario finale dell'intervallo
     */
    public void addTimeInterval(LocalTime start, LocalTime stop) {
        saves.getConfig().addTimeInterval(start, stop);
    }

    /**
     * Imposta la scadenza di una proposta di baratto
     * @param deadLine Scadenza, espressa in numero di giorni
     */
    public void setDeadLine(int deadLine) {
        saves.getConfig().setDeadLine(deadLine);
    }

    public Set<DayOfWeek> getDays() {
        return saves.getConfig().getDays();
    }

    public boolean exists(String luogo) {
        return saves.getConfig().getLuoghi().contains(luogo);
    }

    public Set<String> getLuoghi(){
        return saves.getConfig().getLuoghi();
    }

    public Set<TimeInterval> getTimeIntervals(){
        return saves.getConfig().getTimeIntervals();
    }



    public boolean exists(DayOfWeek day) {
        return saves.getConfig().getDays().contains(day);
    }

    /**
     * Controlla se l'orario passato è valido come inizio dell'intervallo
     * @param startHour Ora iniziale
     * @param startMinutes Minuto iniziale
     * @return 'true' se valido, 'false' se invalido
     */
    public boolean isValidStart(int startHour, int startMinutes) {
        // Non dev'essere contenuto negli altri intervalli
        // Non dev'essere uguale al massimo orario possibile
        return !saves.getConfig().timeIntervalsContain(startHour, startMinutes) || saves.getConfig().isMaxTime(startHour, startMinutes);
    }

    /**
     * Ritorna il limite orario finale per l'ora iniziale passata.
     * Da usare per la validazione degli intervalli orari
     * @param startTime Orario iniziale per l'intervallo in considerazione
     * @return Il massimo orario finale ammissibile
     */
    public LocalTime getStopLimitFor(LocalTime startTime) {
        return saves.getConfig().getMaximumStopAfter(startTime);
    }

    /**
     * Ritorna i minuti ammessi dall'applicazione
     * @return Insieme dei minuti ammessi per un orario
     */
    public Set<Integer> allowedMinutes() {
        return TimeInterval.allowedMinutes();
    }

    public boolean isValidTime(LocalTime proposedTime) {
        for (TimeInterval timeInterval : getTimeIntervals()) {
            if(timeInterval.contains(proposedTime))
                return true;
        }
        return false;
    }

    public boolean isValidDayOfWeek(DayOfWeek dayOfWeek) {
        return getDays().contains(dayOfWeek);
    }
}
