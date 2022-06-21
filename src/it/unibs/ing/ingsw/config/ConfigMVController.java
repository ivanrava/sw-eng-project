package it.unibs.ing.ingsw.config;

import it.unibs.ing.ingsw.auth.User;
import it.unibs.ing.ingsw.exceptions.ConfigImportException;
import it.unibs.ing.ingsw.exceptions.ErrorDialog;
import it.unibs.ing.ingsw.io.DataContainer;
import it.unibs.ing.ingsw.ui.AbstractMVController;
import it.unibs.ing.ingsw.ui.AbstractView;

import java.util.Map;

public class ConfigMVController extends AbstractMVController {

    public static final String PRINT_CONFIG = "Visualizza Configurazione";
    public static final String MODIFY_CONFIG = "Modifica Configurazione";
    public static final String IMPORT_CONFIG = "Importa Configurazione";
    public static final String SQUARE_WILL_NOT_BE_MODIFIED = "Attenzione: La piazza non verrà modificata";
    public static final String IMPORT_SUCCESS = "Configurazione importata con successo :-)";
    private final ConfigController configController;
    private final ConfigView configView;


    public ConfigMVController(DataContainer saves) {
        configController = new ConfigController(saves);
        configView = new ConfigView();
    }

    @Override
    protected void beforeExecute() {
    }

    @Override
    protected Map<String, Runnable> getMenuOptions(User user) {
        return Map.of(
                PRINT_CONFIG, () -> configView.printConfig(configController.getConfig()),
                MODIFY_CONFIG, this::editConfig,
                IMPORT_CONFIG, this::importBatch
        );
    }

    @Override
    protected AbstractView getView() {
        return configView;
    }

    /**
     * Modifica la configurazione
     */
    public void editConfig() {
        hydrateConfig(configController.existsDefaultValues());
    }

    private void hydrateConfig(boolean existsDefaultValues) {
        if (!existsDefaultValues) {
            configController.setPiazza(configView.askSquare());
        }
        if (hasToModify("Scadenza", existsDefaultValues)) configController.setDeadline(configView.askDeadline());
        if (hasToModify("Intervalli temporali", existsDefaultValues))
            configController.setTimeIntervals(configView.askTimeIntervals());
        if (hasToModify("Giorni della settimana", existsDefaultValues)) configController.setDays(configView.askDays());
        if (hasToModify("Luoghi", existsDefaultValues)) configController.setPlaces(configView.askPlaces());


    }

    private boolean hasToModify(String fieldDescription, boolean existsConfig) {
        return !existsConfig || configView.askModify(fieldDescription);
    }

    /**
     * importa la configurazione da un file batch
     */
    private void importBatch() {
        if (configController.existsDefaultValues()) {
            configView.message(SQUARE_WILL_NOT_BE_MODIFIED);
        }
        try {
            String filePath = configView.askPath();
            configController.loadConfigFromBatch(filePath);
            configView.message(IMPORT_SUCCESS);
        } catch (ConfigImportException e) {
            ErrorDialog.print(e);
        }
    }


    public void printConfig() {
        configView.printConfig(configController.getConfig());
    }
}
