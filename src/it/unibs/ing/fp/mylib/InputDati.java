package it.unibs.ing.fp.mylib;

import java.util.*;

public class InputDati implements InputProvider {
    private static final String ERROR_INTEGER_SET = "Puoi inserire solo un valore tra i seguenti: %s\n";
    private static final Scanner lettore = creaScanner();

    private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
    private final static String ERRORE_MINIMO = "Attenzione: e' richiesto un valore maggiore o uguale a ";
    private final static String ERRORE_STRINGA_VUOTA = "Attenzione: non hai inserito alcun carattere";
    private final static String ERRORE_MASSIMO = "Attenzione: e' richiesto un valore minore o uguale a ";
    private final static String MESSAGGIO_AMMISSIBILI = "Attenzione: i caratteri ammissibili sono: ";

    private final static char RISPOSTA_SI = 'S';
    private final static char RISPOSTA_NO = 'N';

    private static Scanner creaScanner() {
        Scanner creato = new Scanner(System.in);
        creato.useDelimiter(System.lineSeparator() + "|\n");
        return creato;
    }

    @Override
    public String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        return lettore.next();
    }

    @Override
    public String leggiStringaNonVuota(String messaggio) {
        boolean finito = false;
        String lettura;
        do {
            lettura = leggiStringa(messaggio);
            lettura = lettura.trim();
            if (lettura.length() > 0) finito = true;
            else System.out.println(ERRORE_STRINGA_VUOTA);
        } while (!finito);
        return lettura;
    }

    public char leggiChar(String messaggio) {
        boolean finito = false;
        char valoreLetto = '\0';
        do {
            System.out.print(messaggio);
            String lettura = lettore.next();
            if (lettura.length() > 0) {
                valoreLetto = lettura.charAt(0);
                finito = true;
            } else {
                System.out.println(ERRORE_STRINGA_VUOTA);
            }
        } while (!finito);
        return valoreLetto;
    }

    public char leggiUpperChar(String messaggio, String ammissibili) {
        boolean finito = false;
        char valoreLetto;
        do {
            valoreLetto = leggiChar(messaggio);
            valoreLetto = Character.toUpperCase(valoreLetto);
            if (ammissibili.indexOf(valoreLetto) != -1) finito = true;
            else System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
        } while (!finito);
        return valoreLetto;
    }

    @Override
    public int leggiIntero(String messaggio) {
        boolean finito = false;
        int valoreLetto = 0;
        do {
            System.out.print(messaggio);
            try {
                valoreLetto = lettore.nextInt();
                finito = true;
            } catch (InputMismatchException e) {
                System.out.println(ERRORE_FORMATO);
                lettore.next();
            }
        } while (!finito);
        return valoreLetto;
    }

    @Override
    public int leggiInteroConMinimo(String messaggio, int minimo) {
        boolean finito = false;
        int valoreLetto;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo) finito = true;
            else System.out.println(ERRORE_MINIMO + minimo);
        } while (!finito);

        return valoreLetto;
    }

    @Override
    public int leggiIntero(String messaggio, int minimo, int massimo) {
        boolean finito = false;
        int valoreLetto;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (valoreLetto >= minimo && valoreLetto <= massimo) finito = true;
            else if (valoreLetto < minimo) System.out.println(ERRORE_MINIMO + minimo);
            else System.out.println(ERRORE_MASSIMO + massimo);
        } while (!finito);

        return valoreLetto;
    }

    @Override
    public boolean yesOrNo(String messaggio) {
        String mioMessaggio = messaggio + "(" + RISPOSTA_SI + "/" + RISPOSTA_NO + ")";
        char valoreLetto = leggiUpperChar(mioMessaggio, RISPOSTA_SI + String.valueOf(RISPOSTA_NO));

        return valoreLetto == RISPOSTA_SI;
    }

    @Override
    public int leggiInteroDaSet(String messaggio, Set<Integer> allowedValues) {
        boolean finito = false;
        int valoreLetto;
        do {
            valoreLetto = leggiIntero(messaggio);
            if (allowedValues.contains(valoreLetto)) finito = true;
            else System.out.printf(ERROR_INTEGER_SET, allowedValues);
        } while (!finito);

        return valoreLetto;
    }
}