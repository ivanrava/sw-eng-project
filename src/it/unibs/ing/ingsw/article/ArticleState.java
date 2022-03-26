package it.unibs.ing.ingsw.article;

import java.io.Serializable;

public enum ArticleState implements Serializable {
    OFFERTA_APERTA("Aperta"),
    OFFERTA_RITIRATA("Ritirata"),
    OFFERTA_ACCOPPIATA("Accoppiata"),
    OFFERTA_SELEZIONATA("Selezionata"),
    OFFERTA_SCAMBIO("In scambio"),
    OFFERTA_CHIUSA("Chiusa");

    private final String text;

    /**
     * Costruttore interno
     * @param text stringa che indica uno degli stati
     */
    ArticleState(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

