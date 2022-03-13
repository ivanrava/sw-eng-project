package it.unibs.ing.ingsw;

import it.unibs.ing.ingsw.config.Config;
import it.unibs.ing.ingsw.io.Saves;
import it.unibs.ing.ingsw.ui.View;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Saves saves = new Saves();
            /*
            //TODO permanente in saves
            //FIXME : DEMO  ADDCONFIG IN CONFIGURATOR VIEW
            ArrayList<String> luoghi = new ArrayList<>();
            luoghi.add("via brescia");
            luoghi.add("via napoli");
            ArrayList<Day> giorni = new ArrayList<>();
            giorni.add(Day.GIOVEDI);
            ArrayList<TimeInterval> intervalli = new ArrayList<>();
            intervalli.add(new TimeInterval(10, 30, 14, 30));
            */


            Config configurazione = new Config(null, null, null, null, 0 );

            View view = new View(saves, configurazione);
            view.execute();
            saves.save();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("La configurazione è presente, ma non riesco a caricarla!");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
