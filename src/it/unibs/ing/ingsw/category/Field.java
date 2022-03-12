package it.unibs.ing.ingsw.category;

public class Field {
    private final boolean required;
    private final String name;
    private final String value;

    /**
     * Costruttore con valore
     * @param required Se il campo è richiesto
     * @param name Nome del campo
     * @param value Valore del campo
     */
    public Field(boolean required, String name, String value) {
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

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return String.format("(\"%s\", %s)", name, required);
    }
}

