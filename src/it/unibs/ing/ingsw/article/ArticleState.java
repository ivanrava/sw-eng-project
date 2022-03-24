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

    public static ArticleState changeState(String newState) {
        ArticleState state = fromString(newState);
        if (state == null){
            return null;
        }
        if (state.equals(OFFERTA_APERTA) || state.equals(OFFERTA_RITIRATA)){
            return state;
        }
        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}

