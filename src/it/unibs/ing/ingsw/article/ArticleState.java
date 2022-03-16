package it.unibs.ing.ingsw.article;

import java.io.Serializable;

public enum ArticleState implements Serializable {
    OFFERTA_APERTA("Offerta Aperta"),
    OFFERTA_RITIRATA("Offerta Ritirata");

    private final String text;

    ArticleState(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

