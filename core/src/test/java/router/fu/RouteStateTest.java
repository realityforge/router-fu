package router.fu;

import static org.testng.Assert.*;

import java.util.HashMap;
import java.util.UUID;
import org.jspecify.annotations.Nullable;
import org.testng.annotations.Test;

public class RouteStateTest extends AbstractRouterFuTest {
    @Test
    public void basicOperation() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final Route route = new Route(
                UUID.randomUUID().toString(), new Segment[0], new Parameter[0], new TestRegExp(), matchCallback);
        final HashMap<Parameter, @Nullable String> parameters = new HashMap<>();
        final Parameter parameter = new Parameter(UUID.randomUUID().toString());
        final String parameterValue = UUID.randomUUID().toString();
        parameters.put(parameter, parameterValue);
        final boolean terminal = true;
        final RouteState state = new RouteState(route, parameters, terminal);

        assertEquals(state.getRoute(), route);
        assertEquals(state.getParameters(), parameters);
        assertEquals(state.isTerminal(), terminal);
        assertEquals(state.getParameterValue(parameter), parameterValue);
    }
}
