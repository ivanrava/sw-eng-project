package it.unibs.ing.ingsw.ui.controllers;

import it.unibs.ing.ingsw.domain.auth.User;
import it.unibs.ing.ingsw.ui.views.AbstractView;

import java.util.LinkedHashMap;

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
