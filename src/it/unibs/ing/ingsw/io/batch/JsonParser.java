package it.unibs.ing.ingsw.io.batch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.TimeInterval;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.DayOfWeek;

public class JsonParser {
    public static final String CONFIG_JSON = "./config.json";
    public static final String CATEGORIES_JSON = "./categories.json";
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
     * @return La configurazione letta dal file
     * @throws FileNotFoundException Se il file non esiste
     * @throws JsonParseException Se il file non è correttamente scritto
     */
    public Config readConfigJson() throws FileNotFoundException, JsonParseException {
        return gson.fromJson(new JsonReader(new FileReader(CONFIG_JSON)), Config.class);
    }

    /**
     * Legge le categorie da ./categories.json
     * @return Le gerarchie di categorie lette da ./categories.json
     * @throws FileNotFoundException Se il file non esiste
     * @throws JsonParseException Se il file non è correttamente scritto
     */
    public Category[] readCategoriesJson() throws FileNotFoundException, JsonParseException {
        return gson.fromJson(new JsonReader(new FileReader(CATEGORIES_JSON)), Category[].class);
    }
}
