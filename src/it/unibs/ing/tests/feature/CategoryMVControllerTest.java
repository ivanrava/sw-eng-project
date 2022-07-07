package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.ui.controllers.CategoryMVController;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import it.unibs.ing.tests.mocks.QueueInputProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class CategoryMVControllerTest extends RedirectSystemOutputBaseTest {

    CategoryMVController categoryMVController;
    QueueInputProvider queueInputProvider;

    @BeforeEach
    void setUp() {
        queueInputProvider = new QueueInputProvider();
        categoryMVController = new CategoryMVController(new InMemoryDataContainer(), queueInputProvider);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testInsertRootCategory() {
        insertRootCategory();
        assertTrue(out.toString().contains(
                "NAME: desc [(\"field\", true), (\"Stato di conservazione\", true), (\"Descrizione libera\", false)]")
        );
    }

    private void insertRootCategory() {
        queueInputProvider.setIntegerInputs(List.of(1, 3, 0));
        queueInputProvider.setStringInputs(
                List.of(
                        "name",
                        "desc",
                        "field"
                )
        );
        queueInputProvider.setBooleanInputs(List.of(true, true, false));
        categoryMVController.execute(null);
    }

    @Test
    void testInsertChildCategory() {
        insertChildCategory();
        assertThat(
                out.toString(),
                containsString(" --> CHILD: child desc [(\"Stato di conservazione\", true), (\"Descrizione libera\", false), (\"field\", true), (\"child field\", false)]")
        );
    }

    private void insertChildCategory() {
        insertRootCategory();
        queueInputProvider.setIntegerInputs(List.of(2, 3, 0));
        queueInputProvider.setStringInputs(
                List.of(
                        "name",
                        "name",
                        "child",
                        "child desc",
                        "child field"
                )
        );
        queueInputProvider.setBooleanInputs(List.of(true, false, false));
        categoryMVController.execute(null);
    }
}