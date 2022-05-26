package it.unibs.ing.ingsw.category;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable {
    public static final String STATO_DI_CONSERVAZIONE = "Stato di conservazione";
    public static final String DESCRIZIONE_LIBERA = "Descrizione libera";
    private final String name;
    private final String description;
    private final Map<String, Field> fields;
    private final Map<String, Category> childrens;
    private Category parent;

    /**
     * Costruttore generale
     *
     * @param name           Nome della categoria
     * @param description    Descrizione della categoria
     * @param isRootCategory 'true' se la categoria è radice, 'false' altrimenti
     */
    public Category(String name, String description, boolean isRootCategory, Map<String, Field> newFields) {
        this.name = name.toUpperCase();
        this.description = description;

        fields = new HashMap<>();

        if (isRootCategory) {
            fields.putAll(getDefaultFields());
        }
        fields.putAll(newFields);

        childrens = new HashMap<>();
        parent = null;
    }

    /**
     * Costruttore parametrizzato per classi figlie
     *
     * @param name        Nome della categoria
     * @param description Descrizione
     * @param newFields   Nuovi campi
     */
    public Category(String name, String description, Map<String, Field> newFields) {
        this(name, description, false, newFields);
    }

    /**
     * Aggiunge una categoria figlia
     *
     * @param childCategory La categoria figlia da aggiungere
     */
    public void addChildCategory(Category childCategory) {
        this.childrens.put(childCategory.name.toUpperCase(), childCategory);
        childCategory.setParent(this);

    }

    /**
     * Controlla se é una categoria radice
     *
     * @return 'true' se é una categoria radice, 'false' altrimenti
     */
    public Boolean isRootCategory() {
        return parent == null;
    }

    /**
     * @return Campi di default per una nuova categoria radice
     */
    public static Map<String, Field> getDefaultFields() {
        Map<String, Field> defaultFields = new HashMap<>();
        defaultFields.put(STATO_DI_CONSERVAZIONE, new Field(true, STATO_DI_CONSERVAZIONE));
        defaultFields.put(DESCRIZIONE_LIBERA, new Field(false, DESCRIZIONE_LIBERA));
        return defaultFields;
    }

    /**
     * @return I campi della categoria, uniti con quelli del padre ricorsivamente
     */
    public Map<String, Field> getFields() {
        if (isRootCategory()) {
            return fields;
        } else {
            Map<String, Field> allFields = new HashMap<>(parent.getFields());
            allFields.putAll(fields);
            return allFields;
        }
    }

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
        return String.format("[name = %s | description = %s | {fields = %s} | {children = %s} ]\n", name, description, fields, childrens);
    }

    /**
     * Metodo che visualizza indentato il nome della categoria e tutte le sotto categorie
     */
    public String onlyNameToString() {
        return onlyNameToString(0);
    }

    /**
     * Metodo che visualizza indentato il nome della categoria e tutte le sotto categorie
     *
     * @param initialPrefixNumber Numero di ripetizioni della stringa prefissa inizialmente
     */
    private String onlyNameToString(int initialPrefixNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%s %s%n", " --> ".repeat(initialPrefixNumber), name, fields.values()));
        for (Category child : childrens.values()) {
            sb.append(child.onlyNameToString(initialPrefixNumber + 1));
        }
        return sb.toString();
    }

    public Map<String, Category> getChildrens() {
        return childrens;
    }

    /**
     * Controlla se la categoria è foglia
     *
     * @return 'true' se è così, 'false' altrimenti
     */
    public boolean isLeaf() {
        return childrens.size() == 0;
    }

    public String getName() {
        return name;
    }

    public Category searchTree(String categoryName) {
        categoryName = categoryName.toUpperCase();
        System.out.println(categoryName);
        System.out.println(this.name.equals(categoryName));
        if (this.name.equals(categoryName)) return this;
        if (isLeaf()) return null;
        for (Category child : childrens.values()) {
            return child.searchTree(categoryName);
        }
        return null;
    }
}
