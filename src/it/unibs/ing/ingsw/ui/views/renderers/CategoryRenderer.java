package it.unibs.ing.ingsw.ui.views.renderers;

import it.unibs.ing.ingsw.domain.business.Category;

public class CategoryRenderer implements SelectableRenderer {
    /**
     * Metodo che visualizza indentato il nome della categoria e tutte le sotto categorie
     *
     * @param initialPrefixNumber Numero di ripetizioni della stringa prefissa inizialmente
     */
    private String hierarchyToString(Category category, int initialPrefixNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s%s: %s %s%n", " --> ".repeat(initialPrefixNumber), category.getName(), category.getDescription(), category.getFields().values()));
        for (Category child : category.getChildren().values()) {
            sb.append(hierarchyToString(child, initialPrefixNumber + 1));
        }
        return sb.toString();
    }

    @Override
    public String render(Object o) {
        return hierarchyToString((Category) o, 0);
    }

    @Override
    public boolean canHandle(Object o) {
        return o instanceof Category;
    }
}
