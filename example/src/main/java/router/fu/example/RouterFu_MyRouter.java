package router.fu.example;

import elemental2.core.RegExp;
import elemental2.dom.Window;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Elemental2HashRoutingBackend;
import router.fu.MatchResult;
import router.fu.PathElement;
import router.fu.PathParameter;
import router.fu.Route;
import router.fu.RouteLocation;
import router.fu.RouteState;
import router.fu.Router;

@Generated( "router.fu" )
@SuppressWarnings( "FieldCanBeLocal" )
final class RouterFu_MyRouter
  extends MyRouter
  implements MyRouterService
{
  private final router.fu.Route $fu$_authFilter =
    new router.fu.Route( "authFilter",
                         null,
                         new PathParameter[]{},
                         new RegExp( "^/.*$" ),
                         ( ( location, route, parameters ) -> authFilterCallback() ) );
  private final router.fu.Route $fu$_regions =
    new router.fu.Route( "regions",
                         new PathElement[]{ new PathElement( "/regions" ) },
                         new PathParameter[]{},
                         new RegExp( "^/regions$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );
  private final router.fu.Route $fu$_regionFilter =
    new router.fu.Route( "regionFilter",
                         null,
                         new PathParameter[]{ new PathParameter( "regionCode" ) },
                         new RegExp( "^/regions/([^/.;]+)/.*$" ),
                         ( ( location, route, parameters ) -> MatchResult.NON_TERMINAL ) );
  private final router.fu.Route $fu$_region =
    new router.fu.Route( "region",
                         new PathElement[]{ new PathElement( "/regions" ),
                                            new PathElement( new PathParameter( "regionCode" ) ) },
                         new PathParameter[]{ new PathParameter( "regionCode" ) },
                         new RegExp( "^/regions/([^/.;]+)$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );
  private final router.fu.Route $fu$_regionEvents =
    new router.fu.Route( "regionEvents",
                         new PathElement[]{ new PathElement( "/regions" ),
                                            new PathElement( new PathParameter( "regionCode" ) ),
                                            new PathElement( "/events" ) },
                         new PathParameter[]{ new PathParameter( "regionCode" ) },
                         new RegExp( "^/regions\\/([^\\/.;]+)\\/events$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );
  private final router.fu.Route $fu$_regionEvent =
    new router.fu.Route( "regionEvent",
                         new PathElement[]{ new PathElement( "/regions" ),
                                            new PathElement( new PathParameter( "regionCode" ) ),
                                            new PathElement( "/event" ),
                                            new PathElement( new PathParameter( "eventId" ) ) },
                         new PathParameter[]{ new PathParameter( "regionCode" ), new PathParameter( "eventId" ) },
                         new RegExp( "^/regions\\/([^\\/.;]+)\\/events\\/([^\\/.;]+)$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );

  private final router.fu.Router $fu$_router;
  private router.fu.RouteLocation $fu$_location;
  private String $fu$_parameter_regionCode;
  private String $fu$_parameter_eventId;

  RouterFu_MyRouter( @Nonnull final Window window )
  {
    final List<Route> routes =
      Collections.unmodifiableList( Arrays.asList( $fu$_authFilter,
                                                   $fu$_regions,
                                                   $fu$_region,
                                                   $fu$_regionEvents,
                                                   $fu$_regionEvent ) );
    $fu$_router = new Router( this::onLocationChanged, new Elemental2HashRoutingBackend( window ), routes );
    $fu$_router.activate();
  }

  @Nonnull
  @Override
  public String buildRegionsLocation()
  {
    return $fu$_regions.buildLocation( Collections.emptyMap() );
  }

  @Override
  public void gotoRegions()
  {
    $fu$_router.changeLocation( buildRegionsLocation() );
  }

  @Nonnull
  @Override
  public String buildRegionLocation( @Nonnull final String regionCode )
  {
    final Map<String, String> parameters = new HashMap<>();
    parameters.put( "regionCode", regionCode );
    return $fu$_regions.buildLocation( parameters );
  }

  @Override
  public void gotoRegion( @Nonnull final String regionCode )
  {
    $fu$_router.changeLocation( buildRegionLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventsLocation( @Nonnull final String regionCode )
  {
    final Map<String, String> parameters = new HashMap<>();
    parameters.put( "regionCode", regionCode );
    return $fu$_regions.buildLocation( parameters );
  }

  @Override
  public void gotoRegionEvents( @Nonnull final String regionCode )
  {
    $fu$_router.changeLocation( buildRegionEventsLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventLocation( @Nonnull final String regionCode, @Nonnull final String eventId )
  {
    final Map<String, String> parameters = new HashMap<>();
    parameters.put( "regionCode", regionCode );
    parameters.put( "eventId", eventId );
    return $fu$_regions.buildLocation( parameters );
  }

  @Override
  public void gotoRegionEvent( @Nonnull final String regionCode, @Nonnull final String eventId )
  {
    $fu$_router.changeLocation( buildRegionEventLocation( regionCode, eventId ) );
  }

  @Nullable
  @Override
  public String getRegionCode()
  {
    return $fu$_parameter_regionCode;
  }

  @Override
  public void setRegionCode( @Nonnull final String regionCode )
  {
    final RouteLocation location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState )
    {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_region )
      {
        gotoRegion( regionCode );
      }
      else if ( route == $fu$_regionEvents )
      {
        gotoRegionEvents( regionCode );
      }
      else if ( route == $fu$_regionEvent )
      {
        gotoRegionEvent( regionCode, terminalState.getParameters().get( "eventId" ) );
      }
    }
  }

  @Nullable
  @Override
  public String getEventId()
  {
    return $fu$_parameter_eventId;
  }

  @Override
  public void setEventId( @Nonnull final String eventId )
  {
    final RouteLocation location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState )
    {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_regionEvent )
      {
        gotoRegionEvent( terminalState.getParameters().get( "regionCode" ), eventId );
      }
    }
  }

  @Nonnull
  public RouteLocation getLocation()
  {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void onLocationChanged( @Nonnull final RouteLocation location )
  {
    $fu$_location = Objects.requireNonNull( location );
    final List<RouteState> states = $fu$_location.getStates();
    final List<Route> routes = $fu$_router.getRoutes();
    final int routeCount = routes.size();
    int routeStartIndex = 0;
    for ( final RouteState state : states )
    {
      for ( int i = routeStartIndex; i < routeCount; i++ )
      {
        if ( routes.get( i ) == state.getRoute() )
        {
          switch ( i )
          {
            case 0:
              break;
            case 1:
              break;
            case 2:
              $fu$_parameter_regionCode = state.getParameters().get( "regionCode" );
              break;
            case 3:
              $fu$_parameter_regionCode = state.getParameters().get( "regionCode" );
              break;
            case 4:
              $fu$_parameter_regionCode = state.getParameters().get( "regionCode" );
              $fu$_parameter_eventId = state.getParameters().get( "eventId" );
              break;
          }
          routeStartIndex = i;
          break;
        }
      }
    }
  }
}
