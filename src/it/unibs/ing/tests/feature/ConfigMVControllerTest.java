package it.unibs.ing.tests.feature;

import it.unibs.ing.fp.mylib.InputDati;
import it.unibs.ing.ingsw.config.ConfigMVController;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ConfigMVControllerTest {

    private ConfigMVController configMVController;
    ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        configMVController = new ConfigMVController(new InMemoryDataContainer(), new InputDati());
        redirectOutput();
    }

    void redirectOutput() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
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
