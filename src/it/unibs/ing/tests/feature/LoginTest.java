package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.ui.AppController;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import it.unibs.ing.tests.mocks.QueueInputProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {

    AppController appController;
    QueueInputProvider testInputProvider;
    ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        testInputProvider = new QueueInputProvider();
        appController = new AppController(new InMemoryDataContainer(), testInputProvider);
        redirectOutput();
    }

    void redirectOutput() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @AfterEach
    void tearDown() {}

    @Test
    void testConfigurator_RegisterLoginLogout() {
        String config_username = "config username";
        testInputProvider.setStringInputs(
                new LinkedList<>(List.of(
                        "default username", "default password",
                        "default username", "default password",
                        config_username, "config password",
                        config_username, "config password"
                        ))
        );
        testInputProvider.setIntegerInputs(
                new LinkedList<>(List.of(2, 2, 0, 0))
        );

        appController.execute();
        assertTrue(out.toString().contains("* Registrazione nuovo utente *"));
        assertTrue(out.toString().contains("Sei dentro, " + config_username));
        assertTrue(out.toString().contains("Uscita dal sistema"));
    }


    @Test
    void testCustomer_RegisterLoginLogout() {
        String customer_username = "customer username";
        testInputProvider.setStringInputs(
                new LinkedList<>(List.of(
                        "default username", "default password",
                        customer_username, "customer password",
                        customer_username, "customer password"
                ))
        );
        testInputProvider.setIntegerInputs(
                new LinkedList<>(List.of(1, 2, 0, 0))
        );

        appController.execute();
        assertTrue(out.toString().contains("* Registrazione nuovo utente *"));
        assertTrue(out.toString().contains("Sei dentro, " + customer_username));
        assertTrue(out.toString().contains("Uscita dal sistema"));
    }
}