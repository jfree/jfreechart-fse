package org.jfree.chart.axis.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.axis.CompassFormat;

/**
 * Tests for the {@link CompassFormat} class.
 */
public class CompassFormatTest extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CompassFormatTest.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CompassFormatTest(String name) {
        super(name);
    }

    public void testDefaultConstructor() {
        final CompassFormat fmt = new CompassFormat();
        assert("N".equals(fmt.getDirectionCode(0)));
        assert("N".equals(fmt.getDirectionCode(360)));
    }

    public void testCustomFormat() {
        final CompassFormat fmt = new CompassFormat();
        final CompassFormat fmtCustom = new CompassFormat("N", "O", "S", "W");
        assert("E".equals(fmt.getDirectionCode(90)));
        assert("O".equals(fmtCustom.getDirectionCode(90)));
        assert("NNO".equals(fmtCustom.getDirectionCode(22.5)));
    }

}
