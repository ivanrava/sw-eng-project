package it.unibs.ing.ingsw.services.persistence;

import it.unibs.ing.ingsw.services.persistence.exceptions.SaveException;

public interface Saveable {
    void save() throws SaveException;
}
