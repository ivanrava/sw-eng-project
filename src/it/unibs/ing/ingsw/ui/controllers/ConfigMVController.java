package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.fp.mylib.InputProvider;
import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.domain.config.ConfigController;
import it.unibs.ing.ingsw.domain.business.exceptions.ConfigImportException;
import it.unibs.ing.ingsw.ui.views.ErrorDialog;
import it.unibs.ing.ingsw.services.persistence.DataContainer;
import it.unibs.ing.ingsw.ui.views.AbstractView;
import it.unibs.ing.ingsw.ui.views.ConfigView;

import java.util.LinkedHashMap;

public class ConfigMVController extends AbstractMVController {

    public static final String PRINT_CONFIG = "Visualizza Configurazione";
    public static final String MODIFY_CONFIG = "Modifica Configurazione";
    public static final String IMPORT_CONFIG = "Importa Configurazione";
    public static final String SQUARE_WILL_NOT_BE_MODIFIED = "Attenzione: La piazza non verrà modificata";
    public static final String IMPORT_SUCCESS = "Configurazione importata con successo :-)";
    private final ConfigController configController;
    private final ConfigView configView;


    public ConfigMVController(DataContainer saves, InputProvider inputProvider) {
        configController = new ConfigController(saves);
        configView = new ConfigView(inputProvider);
    }

    @Override
    protected boolean beforeExecute() {
        return true;
    }

    @Override
    protected LinkedHashMap<String, Runnable> getMenuOptions(User user) {
        LinkedHashMap<String, Runnable> menuOptions = new LinkedHashMap<>();
        menuOptions.put(PRINT_CONFIG, () -> configView.printConfig(configController.getConfig()));
        menuOptions.put(MODIFY_CONFIG, this::editConfig);
        menuOptions.put(IMPORT_CONFIG, this::importBatch);
        return menuOptions;
    }

    @Override
    protected AbstractView getView() {
        return configView;
    }

    /**
     * Modifica la configurazione
     */
    private void editConfig() {
        hydrateConfig(configController.existsDefaultValues());
    }

    private void hydrateConfig(boolean existsDefaultValues) {
        if (!existsDefaultValues) {
            configController.setPiazza(configView.askSquare());
        }
        if (hasToModify("Luoghi", existsDefaultValues)) configController.setPlaces(configView.askPlaces());
        if (hasToModify("Giorni della settimana", existsDefaultValues)) configController.setDays(configView.askDays());
        if (hasToModify("Intervalli temporali", existsDefaultValues))
            configController.setTimeIntervals(configView.askTimeIntervals());
        if (hasToModify("Scadenza", existsDefaultValues)) configController.setDeadline(configView.askDeadline());


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
        if (!configController.existsDefaultValues()) {
            configView.message("La configurazione non è stata ancora caricata dai configuratori.");
        } else {
            configView.printConfig(configController.getConfig());
        }
    }
}
