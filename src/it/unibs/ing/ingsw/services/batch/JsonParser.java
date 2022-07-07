package it.unibs.ing.ingsw.services.batch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import it.unibs.ing.ingsw.domain.business.Category;
import it.unibs.ing.ingsw.domain.config.Config;
import it.unibs.ing.ingsw.domain.config.TimeInterval;
import it.unibs.ing.ingsw.services.batch.deserializers.CategoryDeserializer;
import it.unibs.ing.ingsw.services.batch.deserializers.DayOfWeekDeserializer;
import it.unibs.ing.ingsw.services.batch.deserializers.TimeIntervalDeserializer;
import it.unibs.ing.ingsw.services.batch.exceptions.EmptyConfigException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.DayOfWeek;

public class JsonParser {
    public static final String ERROR_MESSAGE_FILE_VUOTO = "file vuoto";
    public static final String ERROR_MESSAGE_FORMATO_FILE_NON_SUPPORTATO = "formato file non supportato, assicurati di formattare il file come da documentazione";
    private final Gson gson;

    public JsonParser() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DayOfWeek.class, new DayOfWeekDeserializer());
        builder.registerTypeAdapter(TimeInterval.class, new TimeIntervalDeserializer());
        builder.registerTypeAdapter(Category.class, new CategoryDeserializer());
        gson = builder.create();
    }

    /**
     * Legge la configurazione da ./config.json
     * @param filePath percorso assoluto file
     * @return La configurazione letta dal file
     * @throws FileNotFoundException Se il file non esiste
     * @throws JsonParseException Se il file non è correttamente scritto
     */
    public Config readConfigJson(String filePath) throws FileNotFoundException, JsonParseException, EmptyConfigException {
        Config config = gson.fromJson(new JsonReader(new FileReader(filePath)), Config.class);
        if (config == null) throw new EmptyConfigException(ERROR_MESSAGE_FILE_VUOTO);
        if (
                config.getSquare() == null ||
                config.getPlaces() == null || config.getPlaces().isEmpty() ||
                config.getDays() == null || config.getDays().isEmpty() ||
                config.getTimeIntervals() == null || config.getTimeIntervals().isEmpty() ||
                config.getDeadline() <= 0
        ) {
            throw new EmptyConfigException(ERROR_MESSAGE_FORMATO_FILE_NON_SUPPORTATO);
        }
        return config;
    }

    /**
     * Legge le categorie da ./categories.json
     * @param filePath percorso assoluto file
     * @return Le gerarchie di categorie lette da ./categories.json
     * @throws FileNotFoundException Se il file non esiste
     * @throws JsonParseException Se il file non è correttamente scritto
     */
    public Category[] readCategoriesJson(String filePath) throws FileNotFoundException, JsonParseException {
        return gson.fromJson(new JsonReader(new FileReader(filePath)), Category[].class);
    }
}
