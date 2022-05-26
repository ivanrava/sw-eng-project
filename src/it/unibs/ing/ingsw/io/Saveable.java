package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.exceptions.SaveException;

public interface Saveable {
    void save() throws SaveException;
}
