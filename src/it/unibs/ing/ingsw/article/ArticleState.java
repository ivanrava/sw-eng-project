package it.unibs.ing.ingsw.article;

import java.io.Serializable;

public enum ArticleState implements Serializable {
    OFFERTA_APERTA("Aperta"),
    OFFERTA_RITIRATA("Ritirata");

    private final String text;

    ArticleState(final String text) {
        this.text = text;
    }

    public static ArticleState fromString(String text) {
        for (ArticleState state : ArticleState.values()) {
            if (state.text.equalsIgnoreCase(text)) {
                return state;
            }
        }
        return null;
    }

    public static boolean hasState(String text) {
        return fromString(text) != null;
    }

    @Override
    public String toString() {
        return text;
    }
}

