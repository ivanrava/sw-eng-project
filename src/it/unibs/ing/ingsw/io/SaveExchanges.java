package it.unibs.ing.ingsw.io;

import it.unibs.ing.ingsw.article.Exchange;

import java.io.*;
import java.util.Collections;
import java.util.List;

public class SaveExchanges extends AbstractSave<List<Exchange>> {
    public static final String SAVE_EXCHANGES = "./exchanges.dat";

    public SaveExchanges() throws IOException, ClassNotFoundException {}

    @Override
    protected String getSaveFilename() {
        return SAVE_EXCHANGES;
    }

    public List<Exchange> getExchanges() {
        return getSaveObject();
    }

    @Override
    public List<Exchange> defaultSaveObject() {
        return Collections.emptyList();
    }
}
