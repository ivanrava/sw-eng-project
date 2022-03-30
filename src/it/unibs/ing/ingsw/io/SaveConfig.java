package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.config.Config;

import java.io.*;
import java.util.HashSet;
import java.util.TreeSet;

public class SaveConfig implements Serializable {
    public static final String CONFIG_SAVE_FILENAME = "./config.dat";
    private String username;
    private String password;
    private boolean isConfigured = false;
    private final Config config = new Config(new HashSet<>(), new TreeSet<>(), new TreeSet<>(), 1);

    /**
     * Imposta i valori immutabili di configurazione
     * @param username Username di default
     * @param password Password di default
     */
    public void setImmutableValues(String username, String password) {
        if (isConfigured) {
            throw new IllegalArgumentException("I valori obbligatori della Config sono già stati impostati");
        } else {
            this.username = username;
            this.password = password;
            this.isConfigured = true;
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Config getConfig() { return config; }

    /**
     * imposta la configurazione (con controllo sui valori immutabili)
     * @param newConfig
     */
    public void setConfig(Config newConfig) {
        if (!config.isConfiguredImmutableValues()) {
            config.setImmutableValues(newConfig.getSquare());
        }
        config.setPlaces(newConfig.getPlaces());
        config.setDays(newConfig.getDays());
        config.setTimeIntervals(newConfig.getTimeIntervals());
        config.setDeadline(newConfig.getDeadline());
    }

    /**
     * Controlla se esiste il file di configurazione globale
     * @return 'true' se esiste, 'false' altrimenti
     */
    public static boolean exists() {
        File f = new File(CONFIG_SAVE_FILENAME);
        return f.exists();
    }

    /**
     * Carica la configurazione dal filesystem. Se non la trova ne crea una nuova
     * @return La configurazione caricata
     * @throws IOException Errore di I/O durante l'apertura della config (non per file inesistente)
     * @throws ClassNotFoundException Errore durante la lettura dell'oggetto dal file
     */
    public static SaveConfig loadConfig() throws IOException, ClassNotFoundException {
        File f = new File(CONFIG_SAVE_FILENAME);
        if (f.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(f)))) {
                return (SaveConfig) inputStream.readObject();
            }
        }

        return new SaveConfig();
    }

    /**
     * Salva la configurazione sul filesystem.
     * @throws IOException Errore di I/O durante il salvataggio della config
     */
    public void save() throws IOException {
        File f = new File(CONFIG_SAVE_FILENAME);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            outputStream.writeObject(this);
        }
    }

    /**
     * @return 'true' se sono configurate le credenziali di default, 'false' altrimenti
     */
    public boolean isConfiguredDefaultCredentials() {
        return isConfigured;
    }

    /**
     * @return 'true' se sono configurati i valori immutabili di configurazione (es. piazza), 'false' altrimenti
     */
    public boolean isConfiguredImmutableValues() {
        return config.isConfiguredImmutableValues();
    }
}
