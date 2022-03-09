package it.unibs.ing.ingsw.category;

public class Field<T> {
    private final boolean required;
    private final String name;
    private final T value;

    public Field(boolean required, String name, T value) {
        this.required = required;
        this.name = name;
        this.value = value;
    }

    public Field(boolean required, String name) {
        this(required, name, null);
    }

    @Override
    public String toString() {
        return "Field{" +
                "required=" + required +
                ", name='" + name + '\'' +
                '}';
    }
}

