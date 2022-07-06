package it.unibs.ing.tests;

import it.unibs.ing.fp.mylib.InputProvider;

import java.util.Queue;
import java.util.Set;

public class TestInputProvider implements InputProvider {
    private Queue<String> stringInputs;
    private Queue<Integer> integerInputs;
    private Queue<Boolean> booleanInputs;

    public void setStringInputs(Queue<String> stringInputs) {
        this.stringInputs = stringInputs;
    }

    public void setIntegerInputs(Queue<Integer> integerInputs) {
        this.integerInputs = integerInputs;
    }

    public void setBooleanInputs(Queue<Boolean> booleanInputs) {
        this.booleanInputs = booleanInputs;
    }

    @Override
    public String leggiStringa(String messaggio) {
        return stringInputs.remove();
    }

    @Override
    public String leggiStringaNonVuota(String messaggio) {
        return stringInputs.remove();
    }

    @Override
    public int leggiIntero(String messaggio) {
        return integerInputs.remove();
    }

    @Override
    public int leggiInteroConMinimo(String messaggio, int minimo) {
        return integerInputs.remove();
    }

    @Override
    public int leggiIntero(String messaggio, int minimo, int massimo) {
        return integerInputs.remove();
    }

    @Override
    public boolean yesOrNo(String messaggio) {
        return booleanInputs.remove();
    }

    @Override
    public int leggiInteroDaSet(String messaggio, Set<Integer> allowedValues) {
        return integerInputs.remove();
    }
}
