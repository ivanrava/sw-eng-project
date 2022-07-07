package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.domain.config.Config;

import java.io.IOException;
import java.util.HashSet;
import java.util.TreeSet;

public class SaveConfig extends AbstractSave<Config> {
    public static final String ASSERTION_CONFIG_WITHOUT_DEFAULT_CREDENTIALS = "Non si può impostare una nuova configurazione con credenziali di default";
    private final Config config;

    public SaveConfig(String filename) throws IOException, ClassNotFoundException {
        super(filename);
        config = getSaveObject();
    }

    public Config getConfig() { return config; }

    /**
     * imposta la configurazione (con controllo sui valori immutabili)
     * @param newConfig Nuova config da impostare, senza credenziali
     */
    public void setConfig(Config newConfig) {
        assert !newConfig.isConfiguredDefaultCredentials() : ASSERTION_CONFIG_WITHOUT_DEFAULT_CREDENTIALS;
        newConfig.setDefaultCredentials(config.getUsername(), config.getPassword());
        if (!config.isConfiguredImmutableValues()) {
            config.setImmutableValues(newConfig.getSquare());
        }
        config.setPlaces(newConfig.getPlaces());
        config.setDays(newConfig.getDays());
        config.setTimeIntervals(newConfig.getTimeIntervals());
        config.setDeadline(newConfig.getDeadline());
    }

    @Override
    public Config defaultSaveObject() {
        return new Config(new HashSet<>(), new TreeSet<>(), new TreeSet<>(), 1);
    }

    /**
     * @return 'true' se sono configurati i valori immutabili di configurazione (es. piazza), 'false' altrimenti
     */
    public boolean isConfiguredImmutableValues() {
        return config.isConfiguredImmutableValues();
    }
}
