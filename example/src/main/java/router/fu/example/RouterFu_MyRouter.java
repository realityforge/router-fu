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
import org.realityforge.arez.annotations.ArezComponent;
import router.fu.HashBackend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@Generated( "router.fu" )
@ArezComponent( type = "MyRouter", nameIncludesId = false, allowEmpty = true )
@SuppressWarnings( "FieldCanBeLocal" )
class RouterFu_MyRouter
  extends MyRouter
  implements MyRouterService
{
  private final Parameter $fu$_pathParam_regionCode_1 = new Parameter( "regionCode" );
  private final Parameter $fu$_pathParam_eventId_1 = new Parameter( "eventId" );
  private final router.fu.Route $fu$_authFilter =
    new router.fu.Route( "authFilter",
                         null,
                         new Parameter[]{},
                         new RegExp( "^/.*$" ),
                         ( ( location, route, parameters ) -> authFilterCallback() ) );
  private final router.fu.Route $fu$_regions =
    new router.fu.Route( "regions",
                         new Segment[]{ new Segment( "/regions" ) },
                         new Parameter[]{},
                         new RegExp( "^/regions$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );
  private final router.fu.Route $fu$_regionFilter =
    new router.fu.Route( "regionFilter",
                         null,
                         new Parameter[]{ $fu$_pathParam_regionCode_1 },
                         new RegExp( "^/regions/([^/.;]+)/.*$" ),
                         ( ( location, route, parameters ) -> MatchResult.NON_TERMINAL ) );
  private final router.fu.Route $fu$_region =
    new router.fu.Route( "region",
                         new Segment[]{ new Segment( "/regions" ),
                                        new Segment( $fu$_pathParam_regionCode_1 ) },
                         new Parameter[]{ $fu$_pathParam_regionCode_1 },
                         new RegExp( "^/regions/([^/.;]+)$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );
  private final router.fu.Route $fu$_regionEvents =
    new router.fu.Route( "regionEvents",
                         new Segment[]{ new Segment( "/regions" ),
                                        new Segment( $fu$_pathParam_regionCode_1 ),
                                        new Segment( "/events" ) },
                         new Parameter[]{ $fu$_pathParam_regionCode_1 },
                         new RegExp( "^/regions\\/([^\\/.;]+)\\/events$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );
  private final router.fu.Route $fu$_regionEvent =
    new router.fu.Route( "regionEvent",
                         new Segment[]{ new Segment( "/regions" ),
                                        new Segment( $fu$_pathParam_regionCode_1 ),
                                        new Segment( "/event" ),
                                        new Segment( $fu$_pathParam_eventId_1 ) },
                         new Parameter[]{ $fu$_pathParam_regionCode_1, $fu$_pathParam_eventId_1 },
                         new RegExp( "^/regions\\/([^\\/.;]+)\\/events\\/([^\\/.;]+)$" ),
                         ( ( location, route, parameters ) -> MatchResult.TERMINAL ) );

  private final router.fu.Router $fu$_router;
  private Location $fu$_location;
  private String $fu$_parameter_regionCode;
  private String $fu$_parameter_eventId;
  private RouteState $fu$_state_authFilter;
  private RouteState $fu$_state_regions;
  private RouteState $fu$_state_regionFilter;
  private RouteState $fu$_state_region;
  private RouteState $fu$_state_regionEvents;
  private RouteState $fu$_state_regionEvent;

  RouterFu_MyRouter( @Nonnull final Window window )
  {
    final List<Route> routes =
      Collections.unmodifiableList( Arrays.asList( $fu$_authFilter,
                                                   $fu$_regions,
                                                   $fu$_region,
                                                   $fu$_regionEvents,
                                                   $fu$_regionEvent ) );
    $fu$_router = new Router( this::onLocationChanged, new HashBackend( window ), routes );
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
    final Map<Parameter, String> parameters = new HashMap<>();
    parameters.put( $fu$_pathParam_regionCode_1, regionCode );
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
    final Map<Parameter, String> parameters = new HashMap<>();
    parameters.put( $fu$_pathParam_regionCode_1, regionCode );
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
    final Map<Parameter, String> parameters = new HashMap<>();
    parameters.put( $fu$_pathParam_regionCode_1, regionCode );
    parameters.put( $fu$_pathParam_eventId_1, eventId );
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

  void setRegionCode( @Nonnull final String regionCode )
  {
    $fu$_parameter_regionCode = regionCode;
  }

  @Override
  public void updateRegionCode( @Nonnull final String regionCode )
  {
    final Location location = getLocation();
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
        gotoRegionEvent( regionCode, terminalState.getParameterValue( $fu$_pathParam_eventId_1 ) );
      }
    }
  }

  @Nullable
  @Override
  public String getEventId()
  {
    return $fu$_parameter_eventId;
  }

  void setEventId( @Nonnull final String eventId )
  {
    $fu$_parameter_eventId = eventId;
  }

  @Override
  public void updateEventId( @Nonnull final String eventId )
  {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState )
    {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_regionEvent )
      {
        gotoRegionEvent( terminalState.getParameterValue( $fu$_pathParam_regionCode_1 ), eventId );
      }
    }
  }

  @Nonnull
  public Location getLocation()
  {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation( @Nonnull final Location location )
  {
    this.$fu$_location = location;
  }

  @Nullable
  @Override
  public RouteState getAuthFilterRouteState()
  {
    return $fu$_state_authFilter;
  }

  void setAuthFilterRouteState( @Nullable final RouteState state )
  {
    $fu$_state_authFilter = state;
  }

  @Nullable
  @Override
  public RouteState getRegionsRouteState()
  {
    return $fu$_state_regions;
  }

  void setRegionsRouteState( @Nullable final RouteState state )
  {
    $fu$_state_regions = state;
  }

  @Nullable
  @Override
  public RouteState getRegionFilterRouteState()
  {
    return $fu$_state_regionFilter;
  }

  void setRegionFilterRouteState( @Nullable final RouteState state )
  {
    $fu$_state_regionFilter = state;
  }

  @Nullable
  @Override
  public RouteState getRegionRouteState()
  {
    return $fu$_state_region;
  }

  void setRegionRouteState( @Nullable final RouteState state )
  {
    $fu$_state_region = state;
  }

  @Nullable
  @Override
  public RouteState getRegionEventsRouteState()
  {
    return $fu$_state_regionEvents;
  }

  void setRegionEventsRouteState( @Nullable final RouteState state )
  {
    $fu$_state_regionEvents = state;
  }

  @Nullable
  @Override
  public RouteState getRegionEventRouteState()
  {
    return $fu$_state_regionEvent;
  }

  void setRegionEventRouteState( @Nullable final RouteState state )
  {
    $fu$_state_regionEvent = state;
  }

  @Nonnull
  @Override
  public Route getAuthFilterRoute()
  {
    return $fu$_authFilter;
  }

  @Nonnull
  @Override
  public Route getRegionsRoute()
  {
    return $fu$_regions;
  }

  @Nonnull
  @Override
  public Route getRegionFilterRoute()
  {
    return $fu$_regionFilter;
  }

  @Nonnull
  @Override
  public Route getRegionRoute()
  {
    return $fu$_region;
  }

  @Nonnull
  @Override
  public Route getRegionEventsRoute()
  {
    return $fu$_regionEvents;
  }

  @Nonnull
  @Override
  public Route getRegionEventRoute()
  {
    return $fu$_regionEvent;
  }

  void onLocationChanged( @Nonnull final Location location )
  {
    setLocation( Objects.requireNonNull( location ) );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 5; i++ )
    {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i )
      {
        case 0:
          setAuthFilterRouteState( state );
          break;
        case 1:
          setRegionsRouteState( state );
          break;
        case 2:
          setRegionRouteState( state );
          if ( null != state )
          {
            setRegionCode( state.getParameterValue( $fu$_pathParam_regionCode_1 ) );
          }
          break;
        case 3:
          setRegionsRouteState( state );
          if ( null != state )
          {
            setRegionCode( state.getParameterValue( $fu$_pathParam_regionCode_1 ) );
          }
          break;
        case 4:
          setRegionEventRouteState( state );
          if ( null != state )
          {
            setRegionCode( state.getParameterValue( $fu$_pathParam_regionCode_1 ) );
            setEventId( state.getParameterValue( $fu$_pathParam_eventId_1 ) );
          }
          break;
      }
    }
  }
}
