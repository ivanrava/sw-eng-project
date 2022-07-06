package it.unibs.ing.tests.mocks;

import it.unibs.ing.fp.mylib.InputProvider;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class QueueInputProvider implements InputProvider {
    private Queue<String> stringInputs;
    private Queue<Integer> integerInputs;
    private Queue<Boolean> booleanInputs;

    public void setStringInputs(Collection<String> stringInputs) {
        this.stringInputs = new LinkedList<>(stringInputs);
    }

    public void setIntegerInputs(Collection<Integer> integerInputs) {
        this.integerInputs = new LinkedList<>(integerInputs);
    }

    public void setBooleanInputs(Collection<Boolean> booleanInputs) {
        this.booleanInputs = new LinkedList<>(booleanInputs);
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
