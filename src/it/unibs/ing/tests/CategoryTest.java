package it.unibs.ing.tests;

import it.unibs.ing.ingsw.category.Category;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void isLeafOnLeafCategory() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        assertTrue(category.isLeaf());
    }

    @Test
    void isLeafOnNonLeafCategory() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        category.addChildCategory(new Category("child", "descr", false, Collections.emptyMap()));
        assertFalse(category.isLeaf());
    }

    @Test
    void searchTreeFailureWithNoChildAndWrongCategoryName() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        assertNull(category.searchTree("wrongName"));
    }

    @Test
    void searchTreeSuccessWithNoChildAndSameCategoryName() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        assertEquals(category, category.searchTree("name"));
    }

    @Test
    void searchTreeFailureWithChildsAndWrongChildName() {
        Category category = new Category(
                "root",
                "description",
                Collections.emptyMap()
        );
        category.addChildCategory(new Category("child", "descr", false, Collections.emptyMap()));
        assertNull(category.searchTree("wrongName"));
    }

    @Test
    void searchTreeSuccessWithChildsAndSameChildName() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        Category childCategory = new Category("child", "descr", false, Collections.emptyMap());
        category.addChildCategory(childCategory);
        assertEquals(childCategory, category.searchTree("child"));
    }

    @Test
    void searchTreeSuccessWithTwoLevelsOfChild() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        Category childWIthChild = new Category("secondLevel", "descr", false, Collections.emptyMap());
        Category thirdLevelChild = new Category("thirdLevel", "descr", false, Collections.emptyMap());
        childWIthChild.addChildCategory(thirdLevelChild);
        category.addChildCategory(childWIthChild);
        assertEquals(childWIthChild, category.searchTree("secondLevel"));
        assertEquals(thirdLevelChild, category.searchTree("thirdLevel"));
    }

    @Test
    void searchTreeTestCaseInsensitive() {
        Category category = new Category(
                "name",
                "description",
                Collections.emptyMap()
        );
        Category child = new Category("secondLevel", "descr", false, Collections.emptyMap());
        category.addChildCategory(child);
        assertEquals(child, category.searchTree("secondLevel"));
        assertEquals(child, category.searchTree("SECONDLEVEL"));
        assertEquals(child, category.searchTree("SECONDlevel"));
        assertEquals(child, category.searchTree("secondLEVEL"));
    }

    @Test
    void searchTreeNotFirstTree() {
        Category root1 = new Category("Libro", "", true, Collections.emptyMap());
        root1.addChildCategory(new Category("Romanzo", "", false, Collections.emptyMap()));
        root1.addChildCategory(new Category("Saggio", "", false, Collections.emptyMap()));
        root1.getChildren().forEach((s, category) -> System.out.println(category.getName()));
        assertNotNull(root1.searchTree("Saggio"));
        assertNotNull(root1.searchTree("Romanzo"));
    }
}