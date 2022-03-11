package it.unibs.ing.ingsw.category;

import java.util.*;

public class Category {
    // TODO: aggiungere gestione campi
    private final String name;
    private final String description;
    private final List<Field> fields;
    private final Map<String, Category> children;
    private Category parent;

    /**
     * Costruttore generale
     * @param name Nome della categoria
     * @param description Descrizione della categoria
     * @param isRootCategory 'true' se la categoria è radice, 'false' altrimenti
     */
    public Category(String name, String description, boolean isRootCategory) {
        this.name = name;
        this.description = description;

        fields = new ArrayList<>();

        if (isRootCategory) {
            fields.add(new Field<String>(true, "Stato di conservazione"));
            fields.add(new Field<String>(false, "Descrizione libera"));
        }

        children = new HashMap<>();
        parent = null;
    }

    /**
     * Costruttore Category figlia
     * @param name Nome della categoria
     * @param description Descrizione della categoria
     */
    public Category(String name, String description) {
        this(name, description, false);
    }

    /**
     * Aggiunge una categoria figlia
     * @param childCategory La categoria figlia da aggiungere
     */
    public void addChildCategory(Category childCategory) {
        addAllFieldsToCategory(childCategory);
        this.children.put(childCategory.name, childCategory);
        childCategory.setParent(this);
    }

    public void addField(boolean required, String name) {
        fields.add(new Field(required, name));
    }

    public void addAllFieldsToCategory(Category childCategory) {
        childCategory.fields.addAll(fields);
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
        return String.format("[name = %s\ndescription = %s\n{fields = %s}\n{childrens = %s} ]\n\n", name, description, fields, children);
    }

    /**
     * Metodo che visualizza indentato il nome della categoria e tutte le sotto categorie
     * @param initialPrefixNumber Numero di ripetizioni della stringa prefissa inizialmente
     */
    public String onlyNameToString(int initialPrefixNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%s%n", prefix(initialPrefixNumber), name));
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
