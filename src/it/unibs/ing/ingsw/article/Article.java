package it.unibs.ing.ingsw.article;

import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;

import java.io.Serializable;
import java.util.Map;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d - %s:%s\n", id, category.getName(), state));
        for (Map.Entry<String, String> field: fields.entrySet()) {
            sb.append(String.format("\t%s=%s\n", field.getKey(), field.getValue()));
        }
        return sb.toString();
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
}
