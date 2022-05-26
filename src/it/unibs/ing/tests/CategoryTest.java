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
                "name",
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
}