package router.fu;

import java.util.ArrayList;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RouterTest
{
  @Test
  public void activateAndDeactivate()
  {
    final ArrayList<Location> locations = new ArrayList<>();
    final TestBackend backend = new TestBackend();
    final ArrayList<Route> routes = new ArrayList<>();
    final Router router = new Router( locations::add, backend, routes );
    assertEquals( router.getRoutes(), routes );

    assertNull( backend.getHandler() );

    router.activate();

    assertNotNull( backend.getHandler() );
    assertNotNull( router.getCallback() );

    //Locations have location added on activate
    assertEquals( locations.size(), 1 );

    router.deactivate();

    assertNull( backend.getHandler() );
    assertNull( router.getCallback() );
    assertEquals( locations.size(), 1 );

    router.activate();

    assertNotNull( backend.getHandler() );
    assertNotNull( router.getCallback() );
    assertEquals( locations.size(), 2 );

    router.activate();

    assertNotNull( backend.getHandler() );
    assertNotNull( router.getCallback() );
    assertEquals( locations.size(), 3 );

    backend.getHandler().onLocationChange( ValueUtil.randomString() );

    assertEquals( locations.size(), 4 );

    router.deactivate();

    assertNull( backend.getHandler() );
    assertNull( router.getCallback() );
    assertEquals( locations.size(), 4 );
  }

  @Test
  public void changeLocation()
  {
    final TestBackend backend = new TestBackend();
    final Router router = new Router( new ArrayList<Location>()::add, backend, new ArrayList<>() );

    assertEquals( backend.getLocation(), "" );

    final String location = ValueUtil.randomString();
    router.changeLocation( location );

    assertEquals( backend.getLocation(), location );
  }

  @Test
  public void basicRouting()
  {
    final ArrayList<Location> locations = new ArrayList<>();
    final TestBackend backend = new TestBackend();
    final ArrayList<Route> routes = new ArrayList<>();

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

    final Route route1 =
      new Route( "security",
                 null,
                 new Parameter[ 0 ],
                 new TestRegExp( new String[]{ location } ),
                 new TestRouteMatchCallback( MatchResult.NON_TERMINAL ) );
    final Route route2 =
      new Route( "event",
                 null,
                 new Parameter[]{ new Parameter( "eventId" ) },
                 new TestRegExp(),
                 new TestRouteMatchCallback() );
    final Route route3 =
      new Route( "location",
                 null,
                 new Parameter[]{ new Parameter( "location" ) },
                 new TestRegExp(),
                 new TestRouteMatchCallback() );
    final Route route4 =
      new Route( "eventAtLocation",
                 null,
                 new Parameter[]{ new Parameter( "location" ), new Parameter( "eventId" ) },
                 new TestRegExp( new String[]{ location, "ballarat", "42" } ),
                 new TestRouteMatchCallback() );
    // 404 would match everything if it gets to here
    final Route route5 =
      new Route( "404",
                 null,
                 new Parameter[ 0 ],
                 new TestRegExp( new String[]{ location } ),
                 new TestRouteMatchCallback() );

    routes.add( route1 );
    routes.add( route2 );
    routes.add( route3 );
    routes.add( route4 );
    routes.add( route5 );

    final Router router = new Router( locations::add, backend, routes );
    assertEquals( router.getRoutes(), routes );

    final Location result = router.route( location );

    assertNotNull( result );

    assertEquals( result.getPath(), location );
    assertEquals( result.getStates().size(), 2 );
    assertEquals( result.getStates().get( 0 ).getRoute(), route1 );
    assertEquals( result.getStates().get( 1 ).getRoute(), route4 );

    final RouteState terminalState = result.getTerminalState();
    assertNotNull( terminalState );
    assertEquals( terminalState.getRoute(), route4 );
  }
}
