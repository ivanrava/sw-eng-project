package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.ArticleState;
import it.unibs.ing.ingsw.article.ExchangeMVController;
import it.unibs.ing.ingsw.auth.Customer;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import it.unibs.ing.tests.mocks.QueueInputProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


class ExchangeMVControllerTest extends RedirectSystemOutputBaseTest {

    ExchangeMVController exchangeMVController;
    QueueInputProvider queueInputProvider;
    InMemoryDataContainer inMemoryDataContainer;
    Customer customer1;
    Customer customer2;

    @BeforeEach
    void setUp() {
        inMemoryDataContainer = new InMemoryDataContainer();
        customer1 = new Customer("customer1", "password");
        customer2 = new Customer("customer2", "password");
        inMemoryDataContainer.setUsers(Map.of(
                customer1.getUsername(), customer1,
                customer2.getUsername(), customer2));
        Category root = new Category("ROOT", "desc", true, new HashMap<>());
        Category child = new Category("CHILD", "child desc", false, new HashMap<>());
        root.addChildCategory(child);
        inMemoryDataContainer.setHierarchies(Map.of("ROOT", root));
        Article articleCustomer1 = new Article(1, customer1, child, ArticleState.OFFERTA_APERTA, Map.of(
                "Stato di conservazione", "stato1",
                "Descrizione libera", ""
        ));
        Article articleCustomer2 = new Article(2, customer2, child, ArticleState.OFFERTA_APERTA, Map.of(
                "Stato di conservazione", "stato2",
                "Descrizione libera", ""
        ));
        inMemoryDataContainer.setArticles(Map.of(
                1, articleCustomer1,
                2, articleCustomer2));
        queueInputProvider = new QueueInputProvider();
        exchangeMVController = new ExchangeMVController(inMemoryDataContainer, queueInputProvider, Clock.fixed(
                Instant.parse("2022-05-01T10:00:00Z"),
                ZoneOffset.UTC));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void exchangeTest() {
        proposeExchange();
        acceptOffer();
        acceptAppointment();
    }

    /**
     * customer1 propone uno scambio a customer2 e poi esce
     */
    private void proposeExchange() {
        queueInputProvider.setIntegerInputs(List.of(1, 1, 1, 0));
        exchangeMVController.execute(customer1);
        assertEquals(inMemoryDataContainer
                .getArticles()
                .get(1)
                .getState(), ArticleState.OFFERTA_ACCOPPIATA);
        assertEquals(inMemoryDataContainer
                .getArticles()
                .get(2)
                .getState(), ArticleState.OFFERTA_SELEZIONATA);
    }

    /**
     * customer2 accetta la proposta di customer1 e poi esce
     */
    private void acceptOffer() {
        queueInputProvider.setIntegerInputs(List.of(2, 1, 1, 0, 0, 2022, 9, 5, 0));
        queueInputProvider.setBooleanInputs(List.of(true));
        exchangeMVController.execute(customer2);
        assertThat(out.toString(), containsString(
                """
                        1 -> Il 2022-05-01 qualcuno ha proposto lo scambio:
                        \tCategoria: ROOT ->  -> CHILD"""
        ));
        assertThat(out.toString(), containsString("Stato di conservazione=stato2"));
        assertThat(out.toString(), containsString("Stato di conservazione=stato1"));
        assertThat(out.toString(), containsString("Descrizione libera="));
        assertEquals(
                ArticleState.OFFERTA_SCAMBIO,
                inMemoryDataContainer
                    .getArticles()
                    .get(1)
                    .getState());
        assertEquals(
                ArticleState.OFFERTA_SCAMBIO,
                inMemoryDataContainer
                    .getArticles()
                    .get(2)
                    .getState());
    }

    /**
     * customer1 accetta l'appuntamento e poi esce
     */
    private void acceptAppointment() {
        queueInputProvider.setIntegerInputs(List.of(4, 1, 0));
        queueInputProvider.setBooleanInputs(List.of(true));
        exchangeMVController.execute(customer1);
        assertEquals(
                ArticleState.OFFERTA_CHIUSA
                ,inMemoryDataContainer
                        .getArticles()
                        .get(1)
                        .getState());
        assertEquals(
                ArticleState.OFFERTA_CHIUSA
                ,inMemoryDataContainer
                        .getArticles()
                        .get(2)
                        .getState());
    }
}