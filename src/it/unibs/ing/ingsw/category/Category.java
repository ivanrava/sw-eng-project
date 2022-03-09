package it.unibs.ing.ingsw.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Category {
    private final String name;
    private final String description;
    private final List<Field> fields;
    private final List<Category> children;
    private Category parent;

    /**
     * Costruttore root Category
     * @param name
     * @param description
     */
    public Category(String name, String description, boolean isRootCategory) {
        this.name = name;
        this.description = description;

        fields = new ArrayList<>();
        if (isRootCategory) {
            fields.add(new Field<String>(true, "Stato di conservazione"));
            fields.add(new Field<String>(false, "Descrizione libera"));
        }

        children = new ArrayList<>();
        parent = null;
    }

    /**
     * Costruttore child Category
     * @param name
     * @param description
     */
    public Category(String name, String description) {
        this(name, description, false);
    }

    public void addChildCategory(Category childCategory) {
        this.children.add(childCategory);
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

    /*
    private String fieldsAsString() {
        StringBuilder fieldsString = new StringBuilder();
        fieldsString.append("[");
        for (Field field : fields) {
            fieldsString.append(field.toString());
        }
        fieldsString.append("]");

    }
    */
}
