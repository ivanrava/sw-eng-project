package it.unibs.ing.ingsw.ui;

import it.unibs.ing.ingsw.auth.User;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractMVController {

    protected abstract void beforeExecute();

    public void execute(User user) {
        beforeExecute();
        String choice;
        do {
            choice = getView().menu(getMenuOptions(user).keySet());
            if (choice != null)
                getMenuOptions(user).get(choice).run();
        } while (choice != null);
    }

    protected abstract LinkedHashMap<String, Runnable> getMenuOptions(User user);

    protected abstract AbstractView getView();

}
