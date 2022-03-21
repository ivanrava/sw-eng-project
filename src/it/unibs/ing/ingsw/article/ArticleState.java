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

    /**
     * metodo per ritornare valore ArticleState
     * @param text stringa che indica uno degli stati
     * @return ArticleState se il text passato fa parte di uno dei valori di ArticleState, null altrimenti
     */
    public static ArticleState fromString(String text) {
        for (ArticleState state : ArticleState.values()) {
            if (state.text.equalsIgnoreCase(text)) {
                return state;
            }
        }
        return null;
    }

    /**
     * @param text stringa che indica uno degli stati
     * @return 'true' se il text equivale a uno stato esistente, 'false' altrimenti
     */
    public static boolean hasState(String text) {
        return fromString(text) != null;
    }

    @Override
    public String toString() {
        return text;
    }
}

