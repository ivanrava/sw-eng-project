package it.unibs.ing.ingsw.category;

import java.io.Serializable;

public class Field<T> implements Serializable {
    private final boolean required;
    private final String name;
    private final T value;

    /**
     * Costruttore con valore
     * @param required Se il campo è richiesto
     * @param name Nome del campo
     * @param value Valore del campo
     */
    public Field(boolean required, String name, T value) {
        this.required = required;
        this.name = name;
        this.value = value;
    }

    /**
     * Costruttore senza valore
     * @param required Se il campo è richiesto
     * @param name Nome del campo
     */
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

