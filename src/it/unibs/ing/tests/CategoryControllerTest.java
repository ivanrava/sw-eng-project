package it.unibs.ing.tests;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.Exchange;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.category.CategoryController;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.exceptions.SaveException;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.io.Saves;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryControllerTest {

    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(new DataContainer() {
            @Override
            public Config getConfig() {
                return null;
            }

            @Override
            public void setConfig(Config config) {

            }

            @Override
            public Map<String, Category> getHierarchies() {
                return new HashMap<>();
            }

            @Override
            public Map<String, User> getUsers() {
                return null;
            }

            @Override
            public Map<Integer, Article> getArticles() {
                return null;
            }

            @Override
            public List<Exchange> getExchanges() {
                return null;
            }

            @Override
            public String getDefaultUsername() {
                return null;
            }

            @Override
            public String getDefaultPassword() {
                return null;
            }

            @Override
            public void setDefaultCredentials(String username, String password) {

            }

            @Override
            public boolean existsConfiguration() {
                return false;
            }

            @Override
            public boolean existsDefaultCredentials() {
                return false;
            }

            @Override
            public void save() {

            }
        });
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