package it.unibs.ing.tests.feature;

import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class RedirectSystemOutputBaseTest {
    protected ByteArrayOutputStream out;

    @BeforeEach
    final void setUpOutputRedirection() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }
}
