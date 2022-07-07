package it.unibs.ing.ingsw.domain.business;

import it.unibs.ing.ingsw.domain.auth.User;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public class Article implements Serializable {
    private final Category category;
    private final Map<String, String> fields;
    private ArticleState state;
    private final User owner;
    private final int id;

    /**
     * Costruttore parametrizzato
     * @param id Identificatore univoco
     * @param owner Proprietario dell'articolo
     * @param leafCategory Categoria foglia di cui fa parte
     * @param articleState Stato iniziale
     * @param fields Campi della categoria, idealmente valorizzati
     */
    public Article(int id, User owner, Category leafCategory, ArticleState articleState, Map<String, String> fields) {
        this.id = id;
        this.owner = owner;
        this.category = leafCategory;
        this.state = articleState;
        this.fields = fields;
    }

    public Category getCategory() {
        return category;
    }

    public User getOwner() {
        return owner;
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

    public ArticleState getState() {
        return state;
    }

    /**
     * @return 'true' se lo stato dell'articolo é modificabile dall'utente, altrimenti 'false'
     */
    public boolean isEditable(){
        return state == ArticleState.OFFERTA_APERTA || state == ArticleState.OFFERTA_RITIRATA;
    }

    /**
     * @return 'true' se articolo disponibile per uno scambio, altrimenti 'false'
     */
    public boolean isAvailable() {
        return state == ArticleState.OFFERTA_APERTA;
    }

    public int getId() {
        return id;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id && Objects.equals(category, article.category) && Objects.equals(fields, article.fields) && state == article.state && Objects.equals(owner, article.owner);
    }
}
