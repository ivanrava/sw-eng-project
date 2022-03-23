package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Exchange;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveExchanges implements Serializable {
    public static final String SAVE_EXCHANGES = "./exchanges.dat";
    private final List<Exchange> exchanges = new ArrayList<>();

    /**
     * Carica articoli dal filesystem. Se non lo trova ne crea uno nuovo
     * @return articoli caricati
     * @throws IOException Errore di I/O durante l'apertura del file di articoli (non per file inesistente)
     * @throws ClassNotFoundException Errore durante la lettura dell'oggetto dal file
     */
    public static SaveExchanges loadExchanges() throws IOException, ClassNotFoundException {
        File f = new File(SAVE_EXCHANGES);
        if (f.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(f)))) {
                return (SaveExchanges) inputStream.readObject();
            }
        }
        return new SaveExchanges();
    }

    public List<Exchange> getExchanges() {
        return exchanges;
    }

    /**
     * Salva gli articoli sul filesystem
     * @throws IOException Errore di I/O durante il salvataggio della config
     */
    public void saveExchanges() throws IOException {
        File f = new File(SAVE_EXCHANGES);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            outputStream.writeObject(this);
        }
    }
}
