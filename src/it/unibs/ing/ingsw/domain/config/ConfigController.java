package it.unibs.ing.ingsw.domain.config;

import com.google.gson.JsonParseException;
import it.unibs.ing.ingsw.domain.business.exceptions.ConfigImportException;
import it.unibs.ing.ingsw.services.batch.exceptions.EmptyConfigException;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.services.batch.JsonParser;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.util.Set;

public class ConfigController {

    private final DataContainer saves;

    public ConfigController(DataContainer saves) {
        this.saves = saves;
    }

    public boolean existsDefaultValues() {
        return saves.existsConfiguration();
    }

    /**
     * importa la configurazione da un file batch
     *
     * @param filePath percorso assoluto del file
     * @throws ConfigImportException la config non esiste
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
     *
     * @param piazza Piazza da impostare
     */
    public void setPiazza(String piazza) {
        saves.getConfig().setImmutableValues(piazza);
    }

    /**
     * Imposta la scadenza di una proposta di baratto
     *
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

    public Set<String> getPlaces() {
        return saves.getConfig().getPlaces();
    }

    public Set<TimeInterval> getTimeIntervals() {
        return saves.getConfig().getTimeIntervals();
    }

    public void setTimeIntervals(Set<TimeInterval> timeIntervals) {
        saves.getConfig().setTimeIntervals(timeIntervals);
    }

    public void setDays(Set<DayOfWeek> daysOfWeek) {
        saves.getConfig().setDays(daysOfWeek);
    }

    public void setPlaces(Set<String> places) {
        saves.getConfig().setPlaces(places);
    }
}
