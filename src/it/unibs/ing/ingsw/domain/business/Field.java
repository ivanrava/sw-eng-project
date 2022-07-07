package it.unibs.ing.ingsw.domain.business;

import java.io.Serializable;

public record Field(boolean required, String name) implements Serializable {

    @Override
    public String toString() {
        return String.format("(\"%s\", %s)", name, required);
    }

    /**
     * @return 'true' se il campo è di compilazione obbligatoria, 'false' altrimenti
     */
    @Override
    public boolean required() {
        return required;
    }
}

