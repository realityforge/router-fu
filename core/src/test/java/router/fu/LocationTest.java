package router.fu;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class LocationTest extends AbstractRouterFuTest {
    @Test
    public void basicOperation() {
        final RouteState state1 = newRouteState(false);
        final RouteState state2 = newRouteState(false);
        final RouteState state3 = newRouteState(false);
        final RouteState state4 = newRouteState(true);

        final String path = "/path";
        final var location = new Location(path, Arrays.asList(state1, state2, state3, state4));
        assertEquals(path, location.getPath());
        assertEquals(4, location.getStates().size());
        assertEquals(state1, location.getStates().get(0));
        assertEquals(state2, location.getStates().get(1));
        assertEquals(state3, location.getStates().get(2));
        assertEquals(state4, location.getStates().get(3));
        assertEquals(state4, location.getTerminalState());
    }

    @Test
    public void noTerminal() {
        final RouteState state1 = newRouteState(false);
        final RouteState state2 = newRouteState(false);

        final var location = new Location("test-value", Arrays.asList(state1, state2));
        assertEquals(2, location.getStates().size());
        assertEquals(state1, location.getStates().get(0));
        assertEquals(state2, location.getStates().get(1));
        assertNull(location.getTerminalState());
    }

    @Test
    public void noState() {
        final var location = new Location("test-value", Collections.emptyList());
        assertEquals(0, location.getStates().size());
        assertNull(location.getTerminalState());
    }

    private RouteState newRouteState(final boolean terminal) {
        return new RouteState(newRoute(), new HashMap<>(), terminal);
    }

    private Route newRoute() {
        return new Route(
                "test-value", new Segment[0], new Parameter[0], new TestRegExp(), new TestRouteMatchCallback());
    }
}
