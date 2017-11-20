package com.example.parameter;

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
import router.fu.HashBackend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_SelectiveMultiRouteBoundParameter extends SelectiveMultiRouteBoundParameter implements SelectiveMultiRouteBoundParameterService {
  private final Parameter $fu$_eventCode_1643987249 = new Parameter( "eventCode", new RegExp( "[0-9]+" ) );

  private final Parameter $fu$_regionCode_821487049 = new Parameter( "regionCode", new RegExp( "[a-zA-Z]+" ) );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode_821487049 ), }, new Parameter[]{$fu$_regionCode_821487049, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvents = new Route( "regionEvents", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode_821487049 ), new Segment( "/events" ) }, new Parameter[]{$fu$_regionCode_821487049, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvent = new Route( "regionEvent", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode_821487049 ), new Segment( "/events/" ), new Segment( $fu$_eventCode_1643987249 ), }, new Parameter[]{$fu$_regionCode_821487049, $fu$_eventCode_1643987249, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_region;

  private RouteState $fu$_state_regionEvents;

  private RouteState $fu$_state_regionEvent;

  private String $fu$_param_regionCode;

  RouterFu_SelectiveMultiRouteBoundParameter(@Nonnull final Window window) {
    $fu$_router = new Router( this::onLocationChanged, new HashBackend( window ), Collections.unmodifiableList( Arrays.asList( $fu$_route_region, $fu$_route_regionEvents, $fu$_route_regionEvent ) ) );
    $fu$_router.activate();
  }

  @Nonnull
  @Override
  public Route getRegionRoute() {
    return $fu$_route_region;
  }

  @Nullable
  @Override
  public RouteState getRegionRouteState() {
    return $fu$_state_region;
  }

  void setRegionRouteState(@Nullable final RouteState state) {
    $fu$_state_region = state;
  }

  @Nonnull
  @Override
  public Route getRegionEventsRoute() {
    return $fu$_route_regionEvents;
  }

  @Nullable
  @Override
  public RouteState getRegionEventsRouteState() {
    return $fu$_state_regionEvents;
  }

  void setRegionEventsRouteState(@Nullable final RouteState state) {
    $fu$_state_regionEvents = state;
  }

  @Nonnull
  @Override
  public Route getRegionEventRoute() {
    return $fu$_route_regionEvent;
  }

  @Nullable
  @Override
  public RouteState getRegionEventRouteState() {
    return $fu$_state_regionEvent;
  }

  void setRegionEventRouteState(@Nullable final RouteState state) {
    $fu$_state_regionEvent = state;
  }

  @Nullable
  @Override
  public String getRegionCode() {
    return $fu$_param_regionCode;
  }

  void setRegionCode(@Nullable final String value) {
    $fu$_param_regionCode = value;
  }

  @Override
  public void updateRegionCode(@Nonnull final String regionCode) {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState ) {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_route_regionEvents ) {
        gotoRegionEvents(regionCode);
      } else if ( route == $fu$_route_region ) {
        gotoRegion(regionCode);
      }
    }
  }

  @Nonnull
  @Override
  public String buildRegionLocation(@Nonnull final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode_821487049, regionCode );
    return $fu$_route_region.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public void gotoRegion(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildRegionLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventsLocation(@Nonnull final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode_821487049, regionCode );
    return $fu$_route_regionEvents.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public void gotoRegionEvents(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildRegionEventsLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventLocation(@Nonnull final String regionCode, @Nonnull final String eventCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode_821487049, regionCode );
    $fu$_route_params.put( $fu$_eventCode_1643987249, eventCode );
    return $fu$_route_regionEvent.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public void gotoRegionEvent(@Nonnull final String regionCode, @Nonnull final String eventCode) {
    $fu$_router.changeLocation( buildRegionEventLocation( regionCode, eventCode ) );
  }

  @Nonnull
  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(@Nonnull final Location location) {
    $fu$_location = location;
  }

  void onLocationChanged(@Nonnull final Location location) {
    setLocation( Objects.requireNonNull( location ) );
    setRegionCode( null );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 3; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        case 0:;
        if ( null != state && state.getRoute() == $fu$_route_region ) {
          setRegionRouteState( state );
          routeStartIndex++;
          setRegionCode( state.getParameterValue( $fu$_regionCode_821487049 ) );
        } else {
          setRegionRouteState( null );
        }
        break;
        case 1:;
        if ( null != state && state.getRoute() == $fu$_route_regionEvents ) {
          setRegionEventsRouteState( state );
          routeStartIndex++;
          setRegionCode( state.getParameterValue( $fu$_regionCode_821487049 ) );
        } else {
          setRegionEventsRouteState( null );
        }
        break;
        case 2:;
        if ( null != state && state.getRoute() == $fu$_route_regionEvent ) {
          setRegionEventRouteState( state );
          routeStartIndex++;
        } else {
          setRegionEventRouteState( null );
        }
        break;
      }
    }
  }
}
