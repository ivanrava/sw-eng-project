package it.unibs.ing.ingsw.article;

import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.category.Field;

import java.io.Serializable;
import java.util.Map;

public class Article implements Serializable {
    private final Category category;
    private final Map<String, Field> fields;
    private ArticleState state;
    private final User user;
    private final int id;

    /**
     * costruttore
     * @param id identificatore univoco
     * @param owner proprietario
     * @param leafCategory categoria foglia di cui fa parte
     * @param articleState stato attuale
     * @param fields campi
     */
    public Article(int id, User owner, Category leafCategory, ArticleState articleState, Map<String, Field> fields) {
        this.id = id;
        this.user = owner;
        this.category = leafCategory;
        this.state = articleState;
        this.fields = fields;
    }

    public Category getCategory() {
        return category;
    }

    public String getOwnerUsername() {
        return user.getUsername();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%d - %s:%s\n", id, category.getName(), state));
        for (Field field: fields.values()) {
            sb.append(String.format("\t%s=%s\n", field.getName(), field.getValue()));
        }
        return sb.toString();
    }

    public void setState(ArticleState state) {
        this.state = state;
    }

    public ArticleState getState() {
        return state;
    }
}
