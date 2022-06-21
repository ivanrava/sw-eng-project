package it.unibs.ing.ingsw.config;

import com.google.gson.JsonParseException;
import it.unibs.ing.ingsw.exceptions.ConfigImportException;
import it.unibs.ing.ingsw.exceptions.EmptyConfigException;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.io.batch.JsonParser;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class ConfigController {

    private final DataContainer saves;

    public ConfigController(DataContainer saves) {
        this.saves = saves;
    }

    public boolean existsDefaultValues(){
        return saves.existsConfiguration();
    }

    /**
     * importa la configurazione da un file batch
     * @throws ConfigImportException la config non esiste
     * @param filePath percorso assoluto del file
     */
    public void loadConfigFromBatch(String filePath) throws ConfigImportException {
        try {
            JsonParser jsonParser = new JsonParser();
            Config configFromBatch = jsonParser.readConfigJson(filePath);
            saves.setConfig(configFromBatch);
        } catch (FileNotFoundException | JsonParseException | EmptyConfigException e) {
            throw new ConfigImportException(e.getMessage());
        }
    }

    public Config getConfig() {
        return saves.getConfig();
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
    public void setDeadline(int deadLine) {
        saves.getConfig().setDeadline(deadLine);
    }

    public int getDeadline() {
        return saves.getConfig().getDeadline();
    }

    public Set<DayOfWeek> getValidDaysOfWeek() {
        return saves.getConfig().getDays();
    }

    public boolean exists(String luogo) {
        return saves.getConfig().getPlaces().contains(luogo);
    }

    public Set<String> getLuoghi(){
        return saves.getConfig().getPlaces();
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



}
