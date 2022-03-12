package it.unibs.ing.ingsw.category;

import java.io.Serializable;
import java.util.*;

public class Category implements Serializable {
    private final String name;
    private final String description;
    private final Map<String, Field> fields;
    private final Map<String, Category> children;
    private Category parent;

    /**
     * Costruttore generale
     * @param name Nome della categoria
     * @param description Descrizione della categoria
     * @param isRootCategory 'true' se la categoria è radice, 'false' altrimenti
     */
    public Category(String name, String description, boolean isRootCategory, Map<String, Field> newFields) {
        this.name = name;
        this.description = description;

        fields = new HashMap<>();

        if (isRootCategory) {
            fields.putAll(getDefaultFields());
        }
        fields.putAll(newFields);

        children = new HashMap<>();
        parent = null;
    }

    public Category(String name, String description, Map<String, Field> newFields) {
        this(name, description, false, newFields);
    }

    /**
     * Costruttore Category figlia
     * @param name Nome della categoria
     * @param description Descrizione della categoria
     */
    public Category(String name, String description) {
        this(name, description, false, new HashMap<>());
    }

    /**
     * Aggiunge una categoria figlia
     * @param childCategory La categoria figlia da aggiungere
     */
    public void addChildCategory(Category childCategory) {
        this.children.put(childCategory.name, childCategory);
        childCategory.setParent(this);

    }

    public Boolean isRootCategory(){
        return parent == null;
    }

    public static Map<String, Field> getDefaultFields(){
        Map<String, Field> defaultFields = new HashMap<>();
        defaultFields.put("Stato di conservazione", new Field(true, "Stato di conservazione"));
        defaultFields.put("Descrizione libera", new Field(false, "Descrizione libera"));
        return defaultFields;
    }

    public Map<String, Field> getFields() {
        if (isRootCategory()){
            return fields;
        }else {
            Map<String, Field> allFields = new HashMap<>(parent.getFields());
            allFields.putAll(fields);
            return allFields;
        }
    }

    public void addField(boolean required, String name) {
        fields.put(name, new Field(required, name));
    }

    /*
    public void addAllFieldsToCategory(Category childCategory) {
        childCategory.fields.addAll(fields);
    }*/

    private void setParent(Category parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public String toString() {
        return String.format("[name = %s | description = %s | {fields = %s} | {childrens = %s} ]\n", name, description, fields, children);
    }

    /**
     * Metodo che visualizza indentato il nome della categoria e tutte le sotto categorie
     * @param initialPrefixNumber Numero di ripetizioni della stringa prefissa inizialmente
     */
    public String onlyNameToString(int initialPrefixNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%s %s%n", prefix(initialPrefixNumber), name, fields));
        for (Category child : children.values()) {
            sb.append(child.onlyNameToString(initialPrefixNumber + 1));
        }
        return sb.toString();
    }

    public String prefix(int n) {
        return " --> ".repeat(n);
    }


    public Map<String, Category> getChildren() {
        return children;
    }

    /**
     * Controlla se la categoria è foglia
     * @return 'true' se è così, 'false' altrimenti
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    public String getName() {
        return name;
    }
}
