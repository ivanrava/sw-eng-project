package it.unibs.ing.ingsw.services.batch.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.DayOfWeek;

public class DayOfWeekDeserializer implements JsonDeserializer<DayOfWeek> {
    @Override
    public DayOfWeek deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return DayOfWeek.of(jsonElement.getAsInt());
    }
}
