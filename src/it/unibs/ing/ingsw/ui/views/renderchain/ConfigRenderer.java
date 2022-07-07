package it.unibs.ing.ingsw.ui.views.renderchain;

import it.unibs.ing.ingsw.domain.config.Config;

public class ConfigRenderer extends AbstractRenderer{
    public ConfigRenderer(AbstractRenderer next) {
        super(next);
    }

    @Override
    public String render(Object o) {
        if (o instanceof Config config) {
            return "Config{" +
                    "piazza='" + config.getSquare() + '\'' +
                    ", luoghi=" + config.getPlaces() +
                    ", giorni=" + config.getDays() +
                    ", intervalli orari=" + config.getTimeIntervals() +
                    ", scadenza=" + config.getDeadline() +
                    '}';
        }
        return next.render(o);
    }
}
