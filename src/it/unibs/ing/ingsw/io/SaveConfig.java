package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.config.Config;

import java.io.*;
import java.util.HashSet;
import java.util.TreeSet;

public class SaveConfig implements Serializable {
    public static final String CONFIG_SAVE_FILENAME = "./config.dat";
    private String username;
    private String password;
    private final Config config = new Config("", new HashSet<>(), new TreeSet<>(), new TreeSet<>(), 0);

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Config getConfig() { return config; }

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
    public static SaveConfig load() throws IOException, ClassNotFoundException {
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
}
