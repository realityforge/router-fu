package router.fu;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class RouteTest extends AbstractRouterFuTest {
    public static Object[][] pathToRegexData() {
        return new Object[][] {
            {"X", "^X$"},
            {"/abc/1!", "^\\/abc\\/1!$"},
            {"/+$#-?()[]{}.*\\", "^\\/\\+\\$#\\-\\?\\(\\)\\[\\]\\{\\}\\.\\*\\\\$"},
        };
    }

    @Test
    public void pathToPattern() {
        for (final var data : pathToRegexData()) {
            assertEquals(data[1], Route.pathToPattern((String) data[0]));
        }
    }

    @Test
    public void match_noParameters() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final String location = "/locations/42";
        final var matcher = new TestRegExp(location);
        final var route = new Route(name, null, new Parameter[0], matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNotNull(state);
        assert null != state;

        assertEquals(route, state.getRoute());
        assertTrue(state.getParameters().isEmpty());
        assertTrue(state.isTerminal());
    }

    @Test
    public void match_nonTermninal() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback(MatchResult.NON_TERMINAL);
        final String name = "route";
        final String location = "/locations/42";
        final var matcher = new TestRegExp(location);
        final var route = new Route(name, null, new Parameter[0], matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNotNull(state);
        assert null != state;

        assertEquals(route, state.getRoute());
        assertTrue(state.getParameters().isEmpty());
        assertFalse(state.isTerminal());
    }

    @Test
    public void match_matchButCallbackMakesNonMatch() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback(MatchResult.NO_MATCH);
        final String name = "route";
        final String location = "/locations/42";
        final var matcher = new TestRegExp(location);
        final var route = new Route(name, null, new Parameter[0], matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNull(state);
    }

    @Test
    public void match_noMatch() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final Parameter[] parameters = {new Parameter("paramA"), new Parameter("paramB")};
        final String location = "/locations/ballarat/events/42";
        final var matcher = new TestRegExp();
        final var route = new Route(name, null, parameters, matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNull(state);
    }

    @Test
    public void match_simpleParameters() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var paramA = new Parameter("paramA");
        final var paramB = new Parameter("paramB");
        final Parameter[] parameters = {paramA, paramB};
        final String location = "/locations/ballarat/events/42";
        final String[] resultGroups = {location, "ballarat", "42"};

        // Assume a regexp like "^/locations/([^/]+)/events/(\d+)$"
        final var matcher = new TestRegExp(resultGroups);
        final var route = new Route(name, null, parameters, matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNotNull(state);
        assert null != state;

        assertEquals(route, state.getRoute());
        assertEquals(2, state.getParameters().size());
        assertEquals("ballarat", state.getParameters().get(paramA));
        assertEquals("42", state.getParameters().get(paramB));
        assertTrue(state.isTerminal());
    }

    @Test
    public void match_complexParameters() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var paramA = new Parameter("paramA", new TestRegExp("ballarat"));
        final var paramB = new Parameter("paramB", new TestRegExp("42"));
        final String location = "/locations/ballarat/events/42";

        // Assume a regexp like "^/locations/([^/]+)/events/(\d+)$"
        final var matcher = new TestRegExp(location, "ballarat", "42");
        final var route = new Route(name, null, new Parameter[] {paramA, paramB}, matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNotNull(state);
        assert null != state;

        assertEquals(route, state.getRoute());
        assertEquals(2, state.getParameters().size());
        assertEquals("ballarat", state.getParameters().get(paramA));
        assertEquals("42", state.getParameters().get(paramB));
        assertTrue(state.isTerminal());
    }

    @Test
    public void match_complexParameters_noMatch() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final Parameter[] parameters = {
            new Parameter("paramA", new TestRegExp("ballarat")), new Parameter("paramB", new TestRegExp())
        };
        final String location = "/locations/ballarat/events/42";
        final String[] resultGroups = {location, "ballarat", "42"};

        // Assume a regexp like "^/locations/([^/]+)/events/(\d+)$"
        final var matcher = new TestRegExp(resultGroups);
        final var route = new Route(name, null, parameters, matcher, matchCallback);

        final RouteState state = route.match(location);
        assertNull(state);
    }

    @Test
    public void buildLocation_notNavigationTarget() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var route = new Route(name, null, new Parameter[0], new TestRegExp(), matchCallback);
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> route.buildLocation(Collections.emptyMap()));
        assertEquals(
                "Route named '" + name + "' can not have buildPath() invoked on it as is not a target.",
                exception.getMessage());
    }

    @Test
    public void buildLocation_noParameters() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final Segment[] segments = {new Segment("/blah")};
        final Parameter[] parameters = new Parameter[0];
        final String expectedPath = "/blah";
        final String[] resultGroups = {expectedPath};
        final var route = new Route(name, segments, parameters, new TestRegExp(resultGroups), matchCallback);

        final String location = route.buildLocation(Collections.emptyMap());
        assertEquals(expectedPath, location);
    }

    @Test
    public void buildLocation_multipleParameters() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var locationParameter = new Parameter("location");
        final var eventParameter = new Parameter("event");
        final Segment[] segments = {
            new Segment("/locations/"),
            new Segment(locationParameter),
            new Segment("/events/"),
            new Segment(eventParameter)
        };
        final Parameter[] parameters = new Parameter[] {locationParameter, eventParameter};
        final String expectedPath = "/locations/ballarat/events/42";
        final String[] resultGroups = {expectedPath, "ballarat", "42"};
        final var route = new Route(name, segments, parameters, new TestRegExp(resultGroups), matchCallback);

        final HashMap<Parameter, String> input = new HashMap<>();
        input.put(locationParameter, "ballarat");
        input.put(eventParameter, "42");
        final String location = route.buildLocation(input);
        assertEquals(expectedPath, location);
    }

    @Test
    public void buildLocation_missingParameter() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var locationParameter = new Parameter("location");
        final Segment[] segments = {new Segment("/locations/"), new Segment(locationParameter)};
        final Parameter[] parameters = new Parameter[] {locationParameter};
        final String expectedPath = "/locations/ballarat";
        final String[] resultGroups = {expectedPath, "ballarat"};
        final var route = new Route(name, segments, parameters, new TestRegExp(resultGroups), matchCallback);

        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> route.buildLocation(new HashMap<>()));
        assertEquals(
                "Route named '" + name + "' expects a parameter named 'location' to be supplied when "
                        + "building path but no such parameter was supplied. Parameters: {}",
                exception.getMessage());
    }

    @Test
    public void buildLocation_invalidParameter() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        // Event parameters regex will not match thus causing it to fail to match
        final var eventParameter = new Parameter("event", new TestRegExp());
        final Segment[] segments = {new Segment("/events/"), new Segment(eventParameter)};
        final Parameter[] parameters = new Parameter[] {eventParameter};
        final String expectedPath = "/events/42";
        final String[] resultGroups = {expectedPath, "42"};
        final var route = new Route(name, segments, parameters, new TestRegExp(resultGroups), matchCallback);

        final HashMap<Parameter, String> input = new HashMap<>();
        input.put(eventParameter, "42");
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> route.buildLocation(input));
        assertEquals(
                "Route named '" + name + "' has a parameter named 'event' and a value '42' has been "
                        + "passed that does not pass validation check.",
                exception.getMessage());
    }

    @Test
    public void buildLocation_unusedParameter() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var eventParameter = new Parameter("event");
        final Segment[] segments = {new Segment("/events/"), new Segment(eventParameter)};
        final Parameter[] parameters = new Parameter[] {eventParameter};
        final String expectedPath = "/events/42";
        final String[] resultGroups = {expectedPath, "42"};
        final var route = new Route(name, segments, parameters, new TestRegExp(resultGroups), matchCallback);

        final HashMap<Parameter, String> input = new HashMap<>();
        input.put(eventParameter, "42");
        input.put(new Parameter("other"), "73");
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> route.buildLocation(input));
        assertEquals(
                "Route named '" + name + "' expects all parameters to be used when building path but the "
                        + "following parameters are unused. Parameters: [other]",
                exception.getMessage());
    }

    @Test
    public void getParameterByIndex_withBadIndex() {
        final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
        final String name = "route";
        final var route = new Route(name, new Segment[0], new Parameter[0], new TestRegExp(), matchCallback);
        final IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> route.getParameterByIndex(1));
        assertEquals(
                "Route named '" + name + "' expects a parameter at index 1 when matching location "
                        + "but no such parameter has been defined.",
                exception.getMessage());
    }
}
