package router.fu;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import org.testng.annotations.Test;

public class LocationTest extends AbstractRouterFuTest {
    @Test
    public void basicOperation() {
        final RouteState state1 = newRouteState(false);
        final RouteState state2 = newRouteState(false);
        final RouteState state3 = newRouteState(false);
        final RouteState state4 = newRouteState(true);

        final String path = UUID.randomUUID().toString();
        final Location location = new Location(path, Arrays.asList(state1, state2, state3, state4));
        assertEquals(location.getPath(), path);
        assertEquals(location.getStates().size(), 4);
        assertEquals(location.getStates().get(0), state1);
        assertEquals(location.getStates().get(1), state2);
        assertEquals(location.getStates().get(2), state3);
        assertEquals(location.getStates().get(3), state4);
        assertEquals(location.getTerminalState(), state4);
    }

    @Test
    public void noTerminal() {
        final RouteState state1 = newRouteState(false);
        final RouteState state2 = newRouteState(false);

        final Location location = new Location(UUID.randomUUID().toString(), Arrays.asList(state1, state2));
        assertEquals(location.getStates().size(), 2);
        assertEquals(location.getStates().get(0), state1);
        assertEquals(location.getStates().get(1), state2);
        assertNull(location.getTerminalState());
    }

    @Test
    public void noState() {
        final Location location = new Location(UUID.randomUUID().toString(), Collections.emptyList());
        assertEquals(location.getStates().size(), 0);
        assertNull(location.getTerminalState());
    }

    private RouteState newRouteState(final boolean terminal) {
        return new RouteState(newRoute(), new HashMap<>(), terminal);
    }

    private Route newRoute() {
        return new Route(
                UUID.randomUUID().toString(),
                new Segment[0],
                new Parameter[0],
                new TestRegExp(),
                new TestRouteMatchCallback());
    }
}
