package it.unibs.ing.tests.feature;

import it.unibs.ing.ingsw.config.ConfigMVController;
import it.unibs.ing.tests.mocks.InMemoryDataContainer;
import it.unibs.ing.tests.mocks.QueueInputProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class ConfigMVControllerTest extends RedirectSystemOutputBaseTest {

    private ConfigMVController configMVController;
    private QueueInputProvider inputProvider;

    @BeforeEach
    void setUp() {
        inputProvider = new QueueInputProvider();
        configMVController = new ConfigMVController(new InMemoryDataContainer(), inputProvider);
    }

    @Test
    void printConfig() {
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
    void editConfig() {
        inputProvider.setStringInputs(List.of("Luogo 1", "Luogo 2"));
        inputProvider.setBooleanInputs(List.of(true, true, false,
                                                true, true, false,
                                                true, true, false,
                                                true));
        inputProvider.setIntegerInputs(List.of(1, 7,
                8, 0, 10, 0, 12, 30, 14, 30,
                9));
        configMVController.editConfig();
        configMVController.printConfig();
        assertThat(out.toString(), containsString("piazza='square'"));
        assertThat(out.toString(), containsString("Luogo 1"));
        assertThat(out.toString(), containsString("Luogo 2"));
        assertThat(out.toString(), containsString("MONDAY"));
        assertThat(out.toString(), containsString("SUNDAY"));
        assertThat(out.toString(), containsString("intervalli orari=[[08:00, 08:30, 09:00, 09:30, 10:00], [12:30, 13:00, 13:30, 14:00, 14:30]]"));
        assertThat(out.toString(), containsString("scadenza=9"));
    }
}
