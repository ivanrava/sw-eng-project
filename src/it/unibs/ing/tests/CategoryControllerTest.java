package it.unibs.ing.tests;

import it.unibs.ing.ingsw.category.CategoryController;
import it.unibs.ing.ingsw.io.Saves;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryControllerTest {

    @Mock
    private Saves saves;
    private CategoryController categoryController;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        categoryController = new CategoryController(saves);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void testExistsRootWithRootNotFound() {
        assertFalse(categoryController.existsRoot("rootThatDoesntExists"));
    }

    @Test
    public void testExistsRoot() {
        String rootName = "testRoot";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertTrue(categoryController.exists(rootName, rootName));
    }

    @Test
    public void testExistsFailureWIthNoCategories() {
        assertFalse(categoryController.exists("root", "name"));
    }

    @Test
    public void testExistsTrueOnARootCategory() {
        String rootName = "rootName";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertTrue(categoryController.exists(rootName, rootName));
    }

    @Test
    public void testExistsTrueOnNonRootCategory() {
        String rootName = "rootName";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        String testCategoryName = "testCategory";
        categoryController.makeChildCategory(
                rootName,
                rootName,
                testCategoryName,
                "description",
                Collections.emptyMap()
        );
        assertTrue(categoryController.exists(rootName, rootName));
    }

    @Test
    public void testGetRootCategoryCaseInsensitiveFailure() {
        assertNull(categoryController.getRootCategory("test"));
    }

    @Test
    public void testGetRootCategoryCaseInsensitiveSuccess() {
        String rootName = "name";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertNotNull(categoryController.getRootCategory(rootName.toUpperCase()));
        assertNotNull(categoryController.getRootCategory(rootName.toUpperCase()));
    }

    @Test
    public void searchTreeFindRoot() {
        String rootName = "name";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertEquals(
                rootName.toUpperCase(),
                categoryController.searchTree("name", "name").getName()
        );
    }

    @Test
    public void searchTreeFindRootFailureWithWrongName() {
        String rootName = "name";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertNull(categoryController.searchTree("wrongname", "wrongname"));
    }

    @Test
    public void searchTreeFindRootFailureEmptyCategories() {
        assertNull(categoryController.searchTree("name", "name"));
    }


}