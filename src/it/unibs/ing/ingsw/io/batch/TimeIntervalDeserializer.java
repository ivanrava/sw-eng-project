package it.unibs.ing.ingsw.io.batch;

import com.google.gson.*;
import it.unibs.ing.ingsw.config.TimeInterval;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Set;

public class TimeIntervalDeserializer implements JsonDeserializer<TimeInterval> {
    public static final String EXCEPTION_PARSE_MINUTES_MSG = "Puoi utilizzare come minuti (di ogni orario) solo un valore tra quelli preimpostati: ";

    @Override
    public TimeInterval deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();
        LocalTime start, stop;
        Set<Integer> allowedMinutes = TimeInterval.allowedMinutes();
        try {
            start = LocalTime.parse(object.get("start").getAsString());
            stop = LocalTime.parse(object.get("stop").getAsString());
            if (!allowedMinutes.contains(start.getMinute()) || !allowedMinutes.contains(stop.getMinute())) {
                throw new JsonParseException(EXCEPTION_PARSE_MINUTES_MSG + allowedMinutes);
            }
        } catch (DateTimeParseException parseException) {
            throw new JsonParseException(parseException.getMessage());
        }
        return new TimeInterval(start, stop);
    }
}
