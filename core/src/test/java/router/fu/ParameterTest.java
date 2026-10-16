package router.fu;

import static org.testng.Assert.*;

import java.util.UUID;
import org.testng.annotations.Test;

public class ParameterTest extends AbstractRouterFuTest {
    @Test
    public void basicOperation() {
        final String name = UUID.randomUUID().toString();
        final TestRegExp validator = new TestRegExp();
        final Parameter parameter = new Parameter(name, validator);
        assertEquals(parameter.getName(), name);
        assertEquals(parameter.getValidator(), validator);
        assertEquals(parameter.toString(), name);
    }

    @Test
    public void nullValidator() {
        final String name = UUID.randomUUID().toString();
        final Parameter parameter = new Parameter(name);
        assertEquals(parameter.getName(), name);
        assertNull(parameter.getValidator());
        assertEquals(parameter.toString(), name);
    }
}
