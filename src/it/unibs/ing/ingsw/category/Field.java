package it.unibs.ing.ingsw.category;

import java.io.Serializable;

public class Field implements Serializable {
    private final boolean required;
    private final String name;

    /**
     * Costruttore con valore
     * @param required Se il campo è richiesto
     * @param name Nome del campo
     * @param value Valore del campo
     */
    public Field(boolean required, String name) {
        this.required = required;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return String.format("(\"%s\", %s)", name, required);
    }

    public boolean isRequired() {
        return required;
    }
}

