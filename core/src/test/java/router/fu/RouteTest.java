package router.fu;

import javax.annotation.Nonnull;
import org.realityforge.guiceyloops.shared.ValueUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RouteTest
{
  @DataProvider( name = "pathToRegex" )
  public static Object[][] pathToRegexData()
  {
    return new Object[][]{
      { "X", "^X$" },
      { "/abc/1!", "^\\/abc\\/1!$" },
      { "/+$#-?()[]{}.*\\", "^\\/\\+\\$#\\-\\?\\(\\)\\[\\]\\{\\}\\.\\*\\\\$" },
      };
  }

  @Test( dataProvider = "pathToRegex" )
  public void pathToPattern( @Nonnull final String path, @Nonnull final String expected )
    throws Exception
  {
    assertEquals( Route.pathToPattern( path ), expected );
  }

  @Test
  public void match_noParameters()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final String location = ValueUtil.randomString();
    final TestRegExp matcher = new TestRegExp( new String[]{ location } );
    final Route route = new Route( name, null, new PathParameter[ 0 ], matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNotNull( state );

    assertEquals( state.getRoute(), route );
    assertEquals( state.getParameters().isEmpty(), true );
    assertEquals( state.isTerminal(), true );
  }

  @Test
  public void match_nonTermninal()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback( MatchResult.NON_TERMINAL );
    final String name = ValueUtil.randomString();
    final String location = ValueUtil.randomString();
    final TestRegExp matcher = new TestRegExp( new String[]{ location } );
    final Route route = new Route( name, null, new PathParameter[ 0 ], matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNotNull( state );

    assertEquals( state.getRoute(), route );
    assertEquals( state.getParameters().isEmpty(), true );
    assertEquals( state.isTerminal(), false );
  }

  @Test
  public void match_matchButCallbackMakesNonMatch()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback( MatchResult.NO_MATCH );
    final String name = ValueUtil.randomString();
    final String location = ValueUtil.randomString();
    final TestRegExp matcher = new TestRegExp( new String[]{ location } );
    final Route route = new Route( name, null, new PathParameter[ 0 ], matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNull( state );
  }

  @Test
  public void match_noMatch()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final PathParameter[] parameters = {
      new PathParameter( "paramA" ),
      new PathParameter( "paramB" )
    };
    final String location = "/locations/ballarat/events/42";
    final TestRegExp matcher = new TestRegExp();
    final Route route =
      new Route( name, null, parameters, matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNull( state );
  }

  @Test
  public void match_simpleParameters()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final PathParameter[] parameters = {
      new PathParameter( "paramA" ),
      new PathParameter( "paramB" )
    };
    final String location = "/locations/ballarat/events/42";
    final String[] resultGroups = { location, "ballarat", "42" };

    //Assume a regexp like "^/locations/([^/]+)/events/(\d+)$"
    final TestRegExp matcher = new TestRegExp( resultGroups );
    final Route route =
      new Route( name, null, parameters, matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNotNull( state );

    assertEquals( state.getRoute(), route );
    assertEquals( state.getParameters().size(), 2 );
    assertEquals( state.getParameters().get( "paramA" ), "ballarat" );
    assertEquals( state.getParameters().get( "paramB" ), "42" );
    assertEquals( state.isTerminal(), true );
  }

  @Test
  public void match_complexParameters()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final PathParameter[] parameters = {
      new PathParameter( "paramA", new TestRegExp( new String[]{ "ballarat" } ) ),
      new PathParameter( "paramB", new TestRegExp( new String[]{ "42" } ) )
    };
    final String location = "/locations/ballarat/events/42";
    final String[] resultGroups = { location, "ballarat", "42" };

    //Assume a regexp like "^/locations/([^/]+)/events/(\d+)$"
    final TestRegExp matcher = new TestRegExp( resultGroups );
    final Route route =
      new Route( name, null, parameters, matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNotNull( state );

    assertEquals( state.getRoute(), route );
    assertEquals( state.getParameters().size(), 2 );
    assertEquals( state.getParameters().get( "paramA" ), "ballarat" );
    assertEquals( state.getParameters().get( "paramB" ), "42" );
    assertEquals( state.isTerminal(), true );
  }

  @Test
  public void match_complexParameters_noMatch()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final PathParameter[] parameters = {
      new PathParameter( "paramA", new TestRegExp( new String[]{ "ballarat" } ) ),
      new PathParameter( "paramB", new TestRegExp() )
    };
    final String location = "/locations/ballarat/events/42";
    final String[] resultGroups = { location, "ballarat", "42" };

    //Assume a regexp like "^/locations/([^/]+)/events/(\d+)$"
    final TestRegExp matcher = new TestRegExp( resultGroups );
    final Route route =
      new Route( name, null, parameters, matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNull( state );
  }

  @Test
  public void getParameterByIndex_withBadIndex()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Route route =
      new Route( name,
                 new PathElement[ 0 ],
                 new PathParameter[ 0 ],
                 new TestRegExp(),
                 matchCallback );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.getParameterByIndex( 1 ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' expects a parameter at index 1 when matching location " +
                  "but no such parameter has been defined." );
  }
}
