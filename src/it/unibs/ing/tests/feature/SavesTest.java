package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.domain.business.Article;
import it.unibs.ing.ingsw.domain.business.ArticleState;
import it.unibs.ing.ingsw.domain.business.Exchange;
import it.unibs.ing.ingsw.domain.auth.Customer;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.business.Category;
import it.unibs.ing.ingsw.domain.config.Config;
import it.unibs.ing.ingsw.domain.config.TimeInterval;
import it.unibs.ing.ingsw.services.persistence.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.services.persistence.exceptions.SaveException;
import it.unibs.ing.ingsw.services.persistence.Saves;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SavesTest {

    Saves saves;

    @BeforeEach
    void setUp() throws LoadSavesException {
        System.getProperties().setProperty("saves.config", "./tempConfig.dat");
        System.getProperties().setProperty("saves.articles", "./tempArticles.dat");
        System.getProperties().setProperty("saves.categories", "./tempCategories.dat");
        System.getProperties().setProperty("saves.exchanges", "./tempExchanges.dat");
        System.getProperties().setProperty("saves.users", "./tempUsers.dat");
        saves = new Saves();
    }

    @Test
    void testDefaultCollectionsEmpty() {
        assertTrue(saves.getArticles().isEmpty());
        assertTrue(saves.getHierarchies().isEmpty());
        assertTrue(saves.getExchanges().isEmpty());
        assertTrue(saves.getUsers().isEmpty());
    }

    @Test
    void testDefaultConfig() {
        Config actualConfig = saves.getConfig();
        assertTrue(actualConfig.getDays().isEmpty());
        assertTrue(actualConfig.getPlaces().isEmpty());
        assertTrue(actualConfig.getTimeIntervals().isEmpty());
        assertNull(actualConfig.getSquare());
        assertEquals(1, actualConfig.getDeadline());
        assertFalse(saves.existsConfiguration());
    }

    @Test
    void testDefaultCredentials() {
        assertNull(saves.getDefaultUsername());
        assertNull(saves.getDefaultPassword());
        assertFalse(saves.existsDefaultCredentials());
    }

    @Test
    void testSetDefaultCredentials() {
        String username = "username";
        String password = "password";
        saves.setDefaultCredentials(username, password);
        assertEquals(username, saves.getDefaultUsername());
        assertEquals(password, saves.getDefaultPassword());
        assertTrue(saves.existsDefaultCredentials());
        assertFalse(saves.existsConfiguration());
    }

    @Test
    void testSetConfig() {
        String square = "square";
        HashSet<String> places = new HashSet<>();
        places.add("place1");
        places.add("place2");
        TreeSet<DayOfWeek> days = new TreeSet<>();
        days.add(DayOfWeek.MONDAY);
        days.add(DayOfWeek.FRIDAY);
        TreeSet<TimeInterval> timeIntervals = new TreeSet<>();
        timeIntervals.add(new TimeInterval(LocalTime.NOON, LocalTime.MIDNIGHT));
        int deadline = 10;
        Config config = new Config(square, places, days, timeIntervals, deadline);
        saves.setConfig(config);
        Config actualConfig = saves.getConfig();
        assertEquals(square, actualConfig.getSquare());
        assertTrue(actualConfig.getPlaces().containsAll(places));
        assertTrue(actualConfig.getDays().containsAll(days));
        assertTrue(actualConfig.getTimeIntervals().containsAll(timeIntervals));
        assertEquals(10, actualConfig.getDeadline());

        saves.setDefaultCredentials("username", "password");
        assertTrue(saves.existsConfiguration());
    }

    @Test
    void testSaveAndLoad() throws SaveException, LoadSavesException {
        // Saves some data
        Config config = new Config(
                "square",
                Set.of("place1", "place2"),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                Set.of(new TimeInterval(LocalTime.of(0, 0), LocalTime.of(1, 0))),
                5
        );
        saves.setConfig(config);

        User user = new Customer("username", "password");
        saves.getUsers().put(user.getUsername(), user);

        Category category = new Category("name", "descr", true, new HashMap<>());
        saves.getHierarchies().put(category.getName(), category);

        Article article = new Article(0, user, category, ArticleState.OFFERTA_APERTA, new HashMap<>());
        saves.getArticles().put(0, article);

        Article articleProposed = new Article(1, user, category, ArticleState.OFFERTA_APERTA, new HashMap<>());
        Exchange exchange = new Exchange(article, articleProposed, Clock.systemUTC());
        saves.getExchanges().add(exchange);

        saves.save();
        // Reload and asserts
        saves = new Saves();
        assertTrue(saves.getUsers().containsValue(user));
        assertTrue(saves.getHierarchies().containsValue(category));
        assertTrue(saves.getArticles().containsValue(article));
        assertTrue(saves.getExchanges().contains(exchange));
        assertEquals(config, saves.getConfig());
    }

    @AfterEach
    void tearDown() {
        new File("./tempConfig.dat").delete();
        new File("./tempArticles.dat").delete();
        new File("./tempCategories.dat").delete();
        new File("./tempUsers.dat").delete();
        new File("./tempExchanges.dat").delete();
    }
}