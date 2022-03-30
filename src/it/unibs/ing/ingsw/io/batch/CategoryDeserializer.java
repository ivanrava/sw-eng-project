package it.unibs.ing.ingsw.io.batch;

import com.google.gson.*;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.category.Field;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CategoryDeserializer implements JsonDeserializer<Category> {
    @Override
    public Category deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // Create root
        JsonObject object = jsonElement.getAsJsonObject();
        Category root = new Category(
                object.get("name").getAsString(),
                object.get("description").getAsString(),
                true,
                deserializeFields(object.getAsJsonArray("fields"))
        );
        // Add children
        deserializeChildren(root, object.getAsJsonArray("children"));
        // Return root
        return root;
    }

    /**
     * Deserializza i bambini
     * @param parent Categoria genitore a cui "attaccare" i bambini
     * @param children Array JSON dei bambini
     */
    private void deserializeChildren(Category parent, JsonArray children) {
        children.forEach(categoryAsJsonElement -> {
            Category childCategory = new Category(
                    categoryAsJsonElement.getAsJsonObject().get("name").getAsString(),
                    categoryAsJsonElement.getAsJsonObject().get("description").getAsString(),
                    deserializeFields(categoryAsJsonElement.getAsJsonObject().getAsJsonArray("fields"))
            );
            parent.addChildCategory(childCategory);
            deserializeChildren(childCategory, categoryAsJsonElement.getAsJsonObject().getAsJsonArray("children"));
        });
    }

    /**
     * Deserializza i campi
     * @param fieldsArray Array JSON di campi
     * @return Mappa dei campi
     */
    private Map<String, Field> deserializeFields(JsonArray fieldsArray) {
        Map<String, Field> newFields = new HashMap<>();
        fieldsArray.forEach(fieldAsJsonElement -> {
            JsonObject fieldObject = fieldAsJsonElement.getAsJsonObject();
            String fieldName = fieldObject.get("name").getAsString();
            newFields.put(fieldName, new Field(fieldObject.get("required").getAsBoolean(), fieldName));
        });
        return newFields;
    }
}
