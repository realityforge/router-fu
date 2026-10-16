package router.fu;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class RouterTest extends AbstractRouterFuTest {
    @Test
    public void activateAndDeactivate() {
        final ArrayList<Location> locations = new ArrayList<>();
        final var backend = new TestBackend();
        final ArrayList<Route> routes = new ArrayList<>();
        final var router = new Router(locations::add, backend, routes);
        assertEquals(routes, router.getRoutes());

        assertNull(backend.getHandler());

        router.activate();

        assertNotNull(backend.getHandler());
        assert null != backend.getHandler();
        assertNotNull(router.getCallback());
        assert null != router.getCallback();

        // Locations have location added on activate
        assertEquals(1, locations.size());

        router.deactivate();

        assertNull(backend.getHandler());
        assertNull(router.getCallback());
        assertEquals(1, locations.size());

        router.activate();

        assertNotNull(backend.getHandler());
        assert null != backend.getHandler();
        assertNotNull(router.getCallback());
        assert null != router.getCallback();
        assertEquals(2, locations.size());

        router.activate();

        assertNotNull(backend.getHandler());
        assert null != backend.getHandler();
        assertNotNull(router.getCallback());
        assert null != router.getCallback();
        assertEquals(3, locations.size());

        backend.getHandler().onLocationChange("test-value");

        assertEquals(4, locations.size());

        router.deactivate();

        assertNull(backend.getHandler());
        assertNull(router.getCallback());
        assertEquals(4, locations.size());
    }

    @Test
    public void changeLocation() {
        final var backend = new TestBackend();
        final var router = new Router(new ArrayList<Location>()::add, backend, new ArrayList<>());

        assertEquals("", backend.getLocation());

        final String location = "/locations/42";
        router.changeLocation(location);

        assertEquals(location, backend.getLocation());
    }

    @Test
    public void basicRouting() {
        final List<Location> locations = new ArrayList<>();
        final var backend = new TestBackend();
        final List<Route> routes = new ArrayList<>();

        final String location = "/locations/ballarat/events/42";

        /*
         * The following routes:
         *
         * /.* (security)
         * /events/:eventId
         * /locations/:location
         * /locations/:location/events/:eventId
         * /.* (404 not found)
         */

        final var route1 = new Route(
                "security",
                null,
                new Parameter[0],
                new TestRegExp(location),
                new TestRouteMatchCallback(MatchResult.NON_TERMINAL));
        final var route2 = new Route(
                "event",
                null,
                new Parameter[] {new Parameter("eventId")},
                new TestRegExp(),
                new TestRouteMatchCallback());
        final var route3 = new Route(
                "location",
                null,
                new Parameter[] {new Parameter("location")},
                new TestRegExp(),
                new TestRouteMatchCallback());
        final var route4 = new Route(
                "eventAtLocation",
                null,
                new Parameter[] {new Parameter("location"), new Parameter("eventId")},
                new TestRegExp(location, "ballarat", "42"),
                new TestRouteMatchCallback());
        // 404 would match everything if it gets to here
        final var route5 =
                new Route("404", null, new Parameter[0], new TestRegExp(location), new TestRouteMatchCallback());

        routes.add(route1);
        routes.add(route2);
        routes.add(route3);
        routes.add(route4);
        routes.add(route5);

        final var router = new Router(locations::add, backend, routes);
        assertEquals(routes, router.getRoutes());

        final Location result = router.route(location);

        assertNotNull(result);
        assert null != result;

        assertEquals(location, result.getPath());
        assertEquals(2, result.getStates().size());
        assertEquals(route1, result.getStates().get(0).getRoute());
        assertEquals(route4, result.getStates().get(1).getRoute());

        final RouteState terminalState = result.getTerminalState();
        assertNotNull(terminalState);
        assert null != terminalState;
        assertEquals(route4, terminalState.getRoute());
    }
}
