package router.fu;

import static org.junit.Assert.*;

import java.util.HashMap;
import org.jspecify.annotations.Nullable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class RouteStateTest extends AbstractRouterFuTest {
    @Test
    public void basicOperation() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final var route = new Route("test-value", new Segment[0], new Parameter[0], new TestRegExp(), matchCallback);
        final HashMap<Parameter, @Nullable String> parameters = new HashMap<>();
        final var parameter = new Parameter("test-value");
        final String parameterValue = "42";
        parameters.put(parameter, parameterValue);
        final boolean terminal = true;
        final var state = new RouteState(route, parameters, terminal);

        assertEquals(route, state.getRoute());
        assertEquals(parameters, state.getParameters());
        assertEquals(terminal, state.isTerminal());
        assertEquals(parameterValue, state.getParameterValue(parameter));
    }
}
