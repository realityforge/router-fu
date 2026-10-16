package router.fu;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class ParameterTest extends AbstractRouterFuTest {
    @Test
    public void basicOperation() {
        final String name = "route";
        final var validator = new TestRegExp();
        final var parameter = new Parameter(name, validator);
        assertEquals(name, parameter.getName());
        assertEquals(validator, parameter.getValidator());
        assertEquals(name, parameter.toString());
    }

    @Test
    public void nullValidator() {
        final String name = "route";
        final var parameter = new Parameter(name);
        assertEquals(name, parameter.getName());
        assertNull(parameter.getValidator());
        assertEquals(name, parameter.toString());
    }
}
