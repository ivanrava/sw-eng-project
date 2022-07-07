package it.unibs.ing.ingsw.ui.views.renderchain;

import it.unibs.ing.ingsw.domain.config.Config;

public class ConfigRenderer implements SelectableRenderer {
    @Override
    public String render(Object o) {
        Config config = (Config) o;
        return "Config{" +
                "piazza='" + config.getSquare() + '\'' +
                ", luoghi=" + config.getPlaces() +
                ", giorni=" + config.getDays() +
                ", intervalli orari=" + config.getTimeIntervals() +
                ", scadenza=" + config.getDeadline() +
                '}';
    }

    @Override
    public boolean canHandle(Object o) {
        return o instanceof Config;
    }
}
