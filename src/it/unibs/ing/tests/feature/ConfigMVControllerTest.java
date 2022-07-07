package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.domain.config.Config;
import it.unibs.ing.ingsw.ui.controllers.ConfigMVController;
import it.unibs.ing.ingsw.domain.config.TimeInterval;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import it.unibs.ing.tests.mocks.QueueInputProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class ConfigMVControllerTest extends RedirectSystemOutputBaseTest {

    private QueueInputProvider inputProvider;

    @BeforeEach
    void setUp() {
        inputProvider = new QueueInputProvider();
    }

    @Test
    void printConfig() {
        ConfigMVController configMVController = new ConfigMVController(new InMemoryDataContainer(
                new Config("square", Set.of("place1", "place2"), Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), Set.of(new TimeInterval(LocalTime.of(0, 0), LocalTime.of(1, 0))), 5)
        ), inputProvider);
        configMVController.printConfig();
        assertThat(out.toString(), containsString("piazza='square'"));
        assertThat(out.toString(), containsString("place1"));
        assertThat(out.toString(), containsString("place2"));
        assertThat(out.toString(), containsString("MONDAY"));
        assertThat(out.toString(), containsString("TUESDAY"));
        assertThat(out.toString(), containsString("intervalli orari=[[00:00, 00:30, 01:00]]"));
        assertThat(out.toString(), containsString("scadenza=5"));
    }

    @Test
    void editConfigExisting() {
        ConfigMVController configMVController = new ConfigMVController(new InMemoryDataContainer(
                new Config("square", Set.of("place1", "place2"), Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY), Set.of(new TimeInterval(LocalTime.of(0, 0), LocalTime.of(1, 0))), 5)
        ), inputProvider);
        inputProvider.setStringInputs(List.of("Luogo 1", "Luogo 2"));
        inputProvider.setBooleanInputs(List.of(true, true, false,
                                                true, true, false,
                                                true, true, false,
                                                true));
        inputProvider.setIntegerInputs(List.of(2,
                1, 7,
                8, 0, 10, 0, 12, 30, 14, 30,
                9,
                0));
        configMVController.execute(null);
        configMVController.printConfig();
        assertThat(out.toString(), containsString("piazza='square'"));
        assertThat(out.toString(), containsString("Luogo 1"));
        assertThat(out.toString(), containsString("Luogo 2"));
        assertThat(out.toString(), containsString("MONDAY"));
        assertThat(out.toString(), containsString("SUNDAY"));
        assertThat(out.toString(), containsString("intervalli orari=[[08:00, 08:30, 09:00, 09:30, 10:00], [12:30, 13:00, 13:30, 14:00, 14:30]]"));
        assertThat(out.toString(), containsString("scadenza=9"));
    }

    @Test
    void editConfigFirst() {
        ConfigMVController configMVController = new ConfigMVController(new InMemoryDataContainer(), inputProvider);
        inputProvider.setStringInputs(List.of("Piazza", "Luogo 5", "Luogo 6"));
        inputProvider.setBooleanInputs(List.of(true, false,
                true, false,
                true, false));
        inputProvider.setIntegerInputs(List.of(2,
                3, 4,
                8, 0, 10, 0, 12, 30, 14, 30,
                7,
                0));
        configMVController.execute(null);
        configMVController.printConfig();
        assertThat(out.toString(), containsString("piazza='Piazza'"));
        assertThat(out.toString(), containsString("Luogo 5"));
        assertThat(out.toString(), containsString("Luogo 6"));
        assertThat(out.toString(), containsString("WEDNESDAY"));
        assertThat(out.toString(), containsString("THURSDAY"));
        assertThat(out.toString(), containsString("intervalli orari=[[08:00, 08:30, 09:00, 09:30, 10:00], [12:30, 13:00, 13:30, 14:00, 14:30]]"));
        assertThat(out.toString(), containsString("scadenza=7"));
    }
}
