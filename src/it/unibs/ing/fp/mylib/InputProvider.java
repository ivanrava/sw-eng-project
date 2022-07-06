package it.unibs.ing.fp.mylib;

import java.util.Set;

public interface InputProvider {
    String leggiStringa(String messaggio);

    String leggiStringaNonVuota(String messaggio);

    int leggiIntero(String messaggio);

    int leggiInteroConMinimo(String messaggio, int minimo);

    int leggiIntero(String messaggio, int minimo, int massimo);

    boolean yesOrNo(String messaggio);

    int leggiInteroDaSet(String messaggio, Set<Integer> allowedValues);
}
