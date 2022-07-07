package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.article.ArticleMVController;
import it.unibs.ing.ingsw.auth.Customer;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.tests.feature.RedirectSystemOutputBaseTest;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import it.unibs.ing.tests.mocks.QueueInputProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class ArticleMVControllerTest extends RedirectSystemOutputBaseTest {

    ArticleMVController articleMVController;
    QueueInputProvider queueInputProvider;
    InMemoryDataContainer inMemoryDataContainer;
    Customer customer;

    @BeforeEach
    void setUp() {
        queueInputProvider = new QueueInputProvider();
        inMemoryDataContainer = new InMemoryDataContainer();
        customer = new Customer("username", "password");

        Category root = new Category("ROOT", "desc", true, new HashMap<>());
        Category child = new Category("CHILD", "child desc", false, new HashMap<>());
        root.addChildCategory(child);
        inMemoryDataContainer.setHierarchies(Map.of("ROOT", root));
        inMemoryDataContainer.setUsers(Map.of(customer.getUsername(), customer));
        articleMVController = new ArticleMVController(inMemoryDataContainer, queueInputProvider);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void insertArticle() {
        queueInputProvider.setIntegerInputs(List.of(3, 2, 0));
        queueInputProvider.setStringInputs(List.of(
                "root",
                "child",
                "stato di conservazione",
                "",
                "root",
                "child"
        ));
        articleMVController.execute(customer);
        assertThat(
                out.toString(),
                containsString("""
                        ID articolo: 1
                        \tROOT -> CHILD:Aperta
                        \tStato di conservazione=stato di conservazione
                        \tDescrizione libera="""));
    }

    @Test
    void editArticle() {
        insertArticle();
        queueInputProvider.setIntegerInputs(List.of(4, 1, 1, 0));
        queueInputProvider.setBooleanInputs(List.of(true));
        articleMVController.execute(customer);
        assertThat(
                out.toString(),
                containsString("""
                        ID articolo: 1
                        \tROOT -> CHILD:Ritirata
                        \tStato di conservazione=stato di conservazione
                        \tDescrizione libera="""));
    }

}