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

    public void addChildCategory(Category childCategory) {
        this.children.put(childCategory.name, childCategory);
        childCategory.setParent(this);
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
        return String.format("%s (%s), Campi: %s, Categorie figlie:%n\t%s",
                name, description, fields.toString(), children.toString());
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
