package it.unibs.ing.tests;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.exceptions.LoadSavesException;
import it.unibs.ing.ingsw.io.SaveArticles;
import it.unibs.ing.ingsw.io.Saves;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
    }

    @Test
    void testDefaultCredentials() {
        assertNull(saves.getDefaultUsername());
        assertNull(saves.getDefaultPassword());
    }

    @Test
    void testSetDefaultCredentials() {
        String username = "username";
        String password = "password";
        saves.setDefaultCredentials(username, password);
        assertEquals(username, saves.getDefaultUsername());
        assertEquals(password, saves.getDefaultPassword());
    }

    @AfterEach
    void tearDown() {
    }
}