package it.unibs.ing.tests.unit;

import it.unibs.ing.ingsw.category.CategoryController;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest {

    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(new InMemoryDataContainer());
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

    @Test
    public void searchTreeFindSonFailureWithCorrectRoot(){
        String rootName = "name";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        assertNull(categoryController.searchTree(rootName, "child"));
    }

    @Test
    public void searchTreeFindSonCorrectWithCorrectRoot(){
        String rootName = "root";
        String childName = "child";
        categoryController.makeRootCategory(
                rootName,
                "description",
                Collections.emptyMap()
        );
        categoryController.makeChildCategory(rootName, rootName, childName, "description", Collections.emptyMap());
        assertEquals(childName.toUpperCase(),(categoryController.searchTree(rootName,childName).getName()));
    }

}