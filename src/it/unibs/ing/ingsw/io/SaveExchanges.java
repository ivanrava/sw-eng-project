package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Exchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaveExchanges extends AbstractSave<List<Exchange>> {
    public SaveExchanges(String filename) throws IOException, ClassNotFoundException {
        super(filename);
    }


    public List<Exchange> getExchanges() {
        return getSaveObject();
    }

    @Override
    public List<Exchange> defaultSaveObject() {
        return new ArrayList<>();
    }
}
