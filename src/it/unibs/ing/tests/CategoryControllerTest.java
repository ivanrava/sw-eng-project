package it.unibs.ing.tests;

import it.unibs.ing.ingsw.category.CategoryController;
import it.unibs.ing.ingsw.io.Saves;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

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
    void tearDown() throws Exception{
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
    public void testExistsFailure() {
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
        assertNull(categoryController.getRootCategoryCaseInsensitive("test"));
    }

    @Test
    public void testGetRootCategoryCaseInsensitiveSuccess() {
        String rootName = "name";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertNotNull(categoryController.getRootCategoryCaseInsensitive(rootName.toUpperCase()));
        assertNotNull(categoryController.getRootCategoryCaseInsensitive(rootName.toUpperCase()));
    }

}