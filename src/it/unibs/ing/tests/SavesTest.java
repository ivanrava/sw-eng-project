package it.unibs.ing.tests;

import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.TimeInterval;
import it.unibs.ing.ingsw.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.io.Saves;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.TreeSet;

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

    @AfterEach
    void tearDown() {
    }
}