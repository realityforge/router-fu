package router.fu;

import java.util.Collections;
import java.util.HashMap;
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
    final Route route = new Route( name, null, new Parameter[ 0 ], matcher, matchCallback );

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
    final Route route = new Route( name, null, new Parameter[ 0 ], matcher, matchCallback );

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
    final Route route = new Route( name, null, new Parameter[ 0 ], matcher, matchCallback );

    final RouteState state = route.match( location );
    assertNull( state );
  }

  @Test
  public void match_noMatch()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter[] parameters = {
      new Parameter( "paramA" ),
      new Parameter( "paramB" )
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
    final Parameter paramA = new Parameter( "paramA" );
    final Parameter paramB = new Parameter( "paramB" );
    final Parameter[] parameters = { paramA, paramB };
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
    assertEquals( state.getParameters().get( paramA ), "ballarat" );
    assertEquals( state.getParameters().get( paramB ), "42" );
    assertEquals( state.isTerminal(), true );
  }

  @Test
  public void match_complexParameters()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter paramA = new Parameter( "paramA", new TestRegExp( new String[]{ "ballarat" } ) );
    final Parameter paramB = new Parameter( "paramB", new TestRegExp( new String[]{ "42" } ) );
    final Parameter[] parameters = { paramA, paramB };
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
    assertEquals( state.getParameters().get( paramA ), "ballarat" );
    assertEquals( state.getParameters().get( paramB ), "42" );
    assertEquals( state.isTerminal(), true );
  }

  @Test
  public void match_complexParameters_noMatch()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter[] parameters = {
      new Parameter( "paramA", new TestRegExp( new String[]{ "ballarat" } ) ),
      new Parameter( "paramB", new TestRegExp() )
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
  public void buildLocation_notNavigationTarget()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Route route =
      new Route( name,
                 null,
                 new Parameter[ 0 ],
                 new TestRegExp(),
                 matchCallback );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.buildLocation( Collections.emptyMap() ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' can not have buildPath() invoked on it as is not a target." );
  }

  @Test
  public void buildLocation_noParameters()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final PathElement[] pathElements = { new PathElement( "/blah" ) };
    final Parameter[] parameters = new Parameter[ 0 ];
    final String expectedPath = "/blah";
    final String[] resultGroups = { expectedPath };
    final Route route =
      new Route( name, pathElements, parameters, new TestRegExp( resultGroups ), matchCallback );

    final String location = route.buildLocation( Collections.emptyMap() );
    assertEquals( location, expectedPath );
  }

  @Test
  public void buildLocation_multipleParameters()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter locationParameter = new Parameter( "location" );
    final Parameter eventParameter = new Parameter( "event" );
    final PathElement[] pathElements =
      {
        new PathElement( "/locations/" ),
        new PathElement( locationParameter ),
        new PathElement( "/events/" ),
        new PathElement( eventParameter )
      };
    final Parameter[] parameters = new Parameter[]{ locationParameter, eventParameter };
    final String expectedPath = "/locations/ballarat/events/42";
    final String[] resultGroups = { expectedPath, "ballarat", "42" };
    final Route route =
      new Route( name, pathElements, parameters, new TestRegExp( resultGroups ), matchCallback );

    final HashMap<Parameter, String> input = new HashMap<>();
    input.put( locationParameter, "ballarat" );
    input.put( eventParameter, "42" );
    final String location = route.buildLocation( input );
    assertEquals( location, expectedPath );
  }

  @Test
  public void buildLocation_missingParameter()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter locationParameter = new Parameter( "location" );
    final PathElement[] pathElements =
      {
        new PathElement( "/locations/" ),
        new PathElement( locationParameter )
      };
    final Parameter[] parameters = new Parameter[]{ locationParameter };
    final String expectedPath = "/locations/ballarat";
    final String[] resultGroups = { expectedPath, "ballarat" };
    final Route route =
      new Route( name, pathElements, parameters, new TestRegExp( resultGroups ), matchCallback );

    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.buildLocation( new HashMap<>() ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' expects a parameter named 'location' to be supplied when " +
                  "building path but no such parameter was supplied. Parameters: {}" );
  }

  @Test
  public void buildLocation_invalidParameter()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    // Event parameters regex will not match thus causing it to fail to match
    final Parameter eventParameter = new Parameter( "event", new TestRegExp() );
    final PathElement[] pathElements =
      {
        new PathElement( "/events/" ),
        new PathElement( eventParameter )
      };
    final Parameter[] parameters = new Parameter[]{ eventParameter };
    final String expectedPath = "/events/42";
    final String[] resultGroups = { expectedPath, "42" };
    final Route route =
      new Route( name, pathElements, parameters, new TestRegExp( resultGroups ), matchCallback );

    final HashMap<Parameter, String> input = new HashMap<>();
    input.put( eventParameter, "42" );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.buildLocation( input ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' has a parameter named 'event' and a value '42' has been " +
                  "passed that does not pass validation check." );
  }

  @Test
  public void buildLocation_unusedParameter()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter eventParameter = new Parameter( "event" );
    final PathElement[] pathElements =
      {
        new PathElement( "/events/" ),
        new PathElement( eventParameter )
      };
    final Parameter[] parameters = new Parameter[]{ eventParameter };
    final String expectedPath = "/events/42";
    final String[] resultGroups = { expectedPath, "42" };
    final Route route =
      new Route( name, pathElements, parameters, new TestRegExp( resultGroups ), matchCallback );

    final HashMap<Parameter, String> input = new HashMap<>();
    input.put( eventParameter, "42" );
    input.put( new Parameter( "other" ), "73" );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.buildLocation( input ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' expects all parameters to be used when building path but the " +
                  "following parameters are unused. Parameters: [other]" );
  }

  @Test
  public void buildLocation_no_match()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Parameter eventParameter = new Parameter( "event" );
    final PathElement[] pathElements =
      {
        new PathElement( "/events/" ),
        new PathElement( eventParameter )
      };
    final Parameter[] parameters = new Parameter[]{ eventParameter };
    final String expectedPath = "/events/77";
    final String[] resultGroups = { expectedPath, "77" };
    final Route route =
      new Route( name, pathElements, parameters, new TestRegExp( resultGroups ), matchCallback );

    final HashMap<Parameter, String> input = new HashMap<>();
    input.put( eventParameter, "42" );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.buildLocation( input ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' had buildPath() invoked with parameters {event=42} produced " +
                  "path '/events/42' and if this is matched against the same route it produces different " +
                  "parameters: {event=77}" );
  }

  @Test
  public void getParameterByIndex_withBadIndex()
  {
    final RouteMatchCallback matchCallback = new TestRouteMatchCallback();
    final String name = ValueUtil.randomString();
    final Route route =
      new Route( name,
                 new PathElement[ 0 ],
                 new Parameter[ 0 ],
                 new TestRegExp(),
                 matchCallback );
    final IllegalStateException exception =
      expectThrows( IllegalStateException.class, () -> route.getParameterByIndex( 1 ) );
    assertEquals( exception.getMessage(),
                  "Route named '" + name + "' expects a parameter at index 1 when matching location " +
                  "but no such parameter has been defined." );
  }
}
