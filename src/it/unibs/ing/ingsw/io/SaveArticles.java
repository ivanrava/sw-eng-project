package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Article;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveArticles implements Serializable {
    public static final String SAVE_ARTICLES = "./articles.dat";
    private final Map<Integer, Article> articles = new HashMap<>();

    /**
     * Carica articoli dal filesystem. Se non lo trova ne crea uno nuovo
     * @return articoli caricati
     * @throws IOException Errore di I/O durante l'apertura del file di articoli (non per file inesistente)
     * @throws ClassNotFoundException Errore durante la lettura dell'oggetto dal file
     */
    public static SaveArticles loadArticles() throws IOException, ClassNotFoundException {
        File f = new File(SAVE_ARTICLES);
        if (f.exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(f)))) {
                return (SaveArticles) inputStream.readObject();
            }
        }
        return new SaveArticles();
    }

    public Map<Integer, Article> getArticles() {
        return articles;
    }

    /**
     * Salva gli articoli sul filesystem
     * @throws IOException Errore di I/O durante il salvataggio della config
     */
    public void saveArticles() throws IOException {
        File f = new File(SAVE_ARTICLES);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(f)))) {
            outputStream.writeObject(this);
        }
    }
}
