package it.unibs.ing.tests;

import it.unibs.ing.ingsw.article.Article;
import it.unibs.ing.ingsw.article.Exchange;
import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.category.Category;
import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.config.ConfigMVController;
import it.unibs.ing.ingsw.config.TimeInterval;
import it.unibs.ing.ingsw.io.DataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ConfigMVControllerTest {

    private ConfigMVController configMVController;

    @BeforeEach
    void setUp() {
        configMVController = new ConfigMVController(new DataContainer() {
            @Override
            public Config getConfig() {
                return new Config(
                        "square",
                        Set.of("place1", "place2"),
                        Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY),
                        Set.of(new TimeInterval(LocalTime.of(0, 0), LocalTime.of(1, 0))),
                        5
                );
            }

            @Override
            public void setConfig(Config config) {

            }

            @Override
            public Map<String, Category> getHierarchies() {
                return null;
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
    void printConfig() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        configMVController.printConfig();
        assertTrue(out.toString().contains("piazza='square'"));
        assertTrue(out.toString().contains("place1"));
        assertTrue(out.toString().contains("place2"));
        assertTrue(out.toString().contains("MONDAY"));
        assertTrue(out.toString().contains("TUESDAY"));
        assertTrue(out.toString().contains("intervalli orari=[[00:00, 00:30, 01:00]]"));
        assertTrue(out.toString().contains("scadenza=5"));
    }
}
