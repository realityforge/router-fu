package com.example.arez;

import akasha.core.RegExp;
import arez.annotations.Action;
import arez.annotations.ArezComponent;
import arez.annotations.Executor;
import arez.annotations.Feature;
import arez.annotations.Observable;
import arez.annotations.Observe;
import arez.annotations.OnDepsChange;
import arez.annotations.PostConstruct;
import arez.annotations.PreDispose;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import router.fu.Backend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@ArezComponent(
    name = "CompleteRouter",
    requireId = Feature.DISABLE,
    disposeNotifier = Feature.DISABLE
)
@Generated("router.fu.processor.RouterProcessor")
public abstract class RouterFu_CompleteRouter extends CompleteRouter implements CompleteRouterService {
  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Parameter $fu$_eventCode_1643987249 = new Parameter( "eventCode", new RegExp( "^[0-9]+$" ) );

  private final Parameter $fu$_regionCode_821487049 = new Parameter( "regionCode", new RegExp( "^[a-zA-Z]+$" ) );

  private final Route $fu$_route_regionFilter = new Route( "regionFilter", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> regionFilterCallback(  ) );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode_821487049 ), }, new Parameter[]{$fu$_regionCode_821487049, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvents = new Route( "regionEvents", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode_821487049 ), new Segment( "/events" ) }, new Parameter[]{$fu$_regionCode_821487049, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)/events$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvent = new Route( "regionEvent", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode_821487049 ), new Segment( "/events/" ), new Segment( $fu$_eventCode_1643987249 ), }, new Parameter[]{$fu$_regionCode_821487049, $fu$_eventCode_1643987249, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)/events/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_regionFilter;

  private RouteState $fu$_state_region;

  private RouteState $fu$_state_regionEvents;

  private RouteState $fu$_state_regionEvent;

  private String $fu$_param_regionCode;

  private String $fu$_param_eventCode;

  private String $fu$_param_eventCode2;

  RouterFu_CompleteRouter(@Nonnull final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList( $fu$_route_regionFilter, $fu$_route_region, $fu$_route_regionEvents, $fu$_route_regionEvent ) ) );
    $fu$_location = new Location( "", Collections.emptyList() );
  }

  @PostConstruct
  void postConstruct() {
    $fu$_router.activate();
  }

  @PreDispose
  void preDispose() {
    $fu$_router.deactivate();
  }

  @Nonnull
  @Override
  public Route getRegionFilterRoute() {
    return $fu$_route_regionFilter;
  }

  @Nullable
  @Observable
  @Override
  public RouteState getRegionFilterRouteState() {
    return $fu$_state_regionFilter;
  }

  void setRegionFilterRouteState(@Nullable final RouteState state) {
    $fu$_state_regionFilter = state;
  }

  @Nonnull
  @Override
  public Parameter getRegionFilterRegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Observe(
      executor = Executor.EXTERNAL
  )
  MatchResult regionFilterCallback() {
    return super.regionFilterCallback();
  }

  @OnDepsChange
  void onRegionFilterCallbackDepsChange() {
    reRoute();
  }

  @Nonnull
  @Override
  public Route getRegionRoute() {
    return $fu$_route_region;
  }

  @Nullable
  @Observable
  @Override
  public RouteState getRegionRouteState() {
    return $fu$_state_region;
  }

  void setRegionRouteState(@Nullable final RouteState state) {
    $fu$_state_region = state;
  }

  @Nonnull
  @Override
  public Parameter getRegionRegionCodeParameter() {
    return $fu$_regionCode_821487049;
  }

  @Nonnull
  @Override
  public Route getRegionEventsRoute() {
    return $fu$_route_regionEvents;
  }

  @Nullable
  @Observable
  @Override
  public RouteState getRegionEventsRouteState() {
    return $fu$_state_regionEvents;
  }

  void setRegionEventsRouteState(@Nullable final RouteState state) {
    $fu$_state_regionEvents = state;
  }

  @Nonnull
  @Override
  public Parameter getRegionEventsRegionCodeParameter() {
    return $fu$_regionCode_821487049;
  }

  @Nonnull
  @Override
  public Route getRegionEventRoute() {
    return $fu$_route_regionEvent;
  }

  @Nullable
  @Observable
  @Override
  public RouteState getRegionEventRouteState() {
    return $fu$_state_regionEvent;
  }

  void setRegionEventRouteState(@Nullable final RouteState state) {
    $fu$_state_regionEvent = state;
  }

  @Nonnull
  @Override
  public Parameter getRegionEventRegionCodeParameter() {
    return $fu$_regionCode_821487049;
  }

  @Nonnull
  @Override
  public Parameter getRegionEventEventCodeParameter() {
    return $fu$_eventCode_1643987249;
  }

  @Nullable
  @Observable
  @Override
  public String getRegionCode() {
    return $fu$_param_regionCode;
  }

  void setRegionCode(@Nullable final String value) {
    $fu$_param_regionCode = value;
  }

  @Action
  @Override
  public void updateRegionCode(@Nonnull final String regionCode) {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState ) {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_route_region ) {
        gotoRegion(regionCode);
      } else if ( route == $fu$_route_regionEvents ) {
        gotoRegionEvents(regionCode);
      } else if ( route == $fu$_route_regionEvent ) {
        gotoRegionEvent(regionCode, terminalState.getParameterValue( $fu$_eventCode_1643987249 ));
      }
    }
  }

  @Nullable
  @Observable
  @Override
  public String getEventCode() {
    return $fu$_param_eventCode;
  }

  void setEventCode(@Nullable final String value) {
    $fu$_param_eventCode = value;
  }

  @Action
  @Override
  public void updateEventCode(@Nonnull final String eventCode) {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState ) {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_route_regionEvent ) {
        gotoRegionEvent(terminalState.getParameterValue( $fu$_regionCode_821487049 ), eventCode);
      }
    }
  }

  @Nullable
  @Observable
  @Override
  public String getEventCode2() {
    return $fu$_param_eventCode2;
  }

  void setEventCode2(@Nullable final String value) {
    $fu$_param_eventCode2 = value;
  }

  @Action
  @Override
  public void updateEventCode2(@Nonnull final String eventCode2) {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState ) {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_route_regionEvent ) {
        gotoRegionEvent(terminalState.getParameterValue( $fu$_regionCode_821487049 ), eventCode2);
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

  @Override
  public void gotoRegionEvents(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildRegionEventsLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventLocation(@Nonnull final String regionCode,
      @Nonnull final String eventCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode_821487049, regionCode );
    $fu$_route_params.put( $fu$_eventCode_1643987249, eventCode );
    return $fu$_route_regionEvent.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoRegionEvent(@Nonnull final String regionCode, @Nonnull final String eventCode) {
    $fu$_router.changeLocation( buildRegionEventLocation( regionCode, eventCode ) );
  }

  @Nonnull
  @Observable
  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(@Nonnull final Location location) {
    $fu$_location = location;
  }

  @Action
  void onLocationChanged(@Nonnull final Location location) {
    setLocation( Objects.requireNonNull( location ) );
    setRegionCode( null );
    setEventCode( null );
    setEventCode2( null );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 4; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        case 0: {
          if ( null != state && state.getRoute() == $fu$_route_regionFilter ) {
            setRegionFilterRouteState( state );
            routeStartIndex++;
            setRegionCode( state.getParameterValue( $fu$_regionCode ) );
          } else {
            setRegionFilterRouteState( null );
          }
          break;
        } case 1: {
          if ( null != state && state.getRoute() == $fu$_route_region ) {
            setRegionRouteState( state );
            routeStartIndex++;
            setRegionCode( state.getParameterValue( $fu$_regionCode_821487049 ) );
          } else {
            setRegionRouteState( null );
          }
          break;
        } case 2: {
          if ( null != state && state.getRoute() == $fu$_route_regionEvents ) {
            setRegionEventsRouteState( state );
            routeStartIndex++;
            setRegionCode( state.getParameterValue( $fu$_regionCode_821487049 ) );
          } else {
            setRegionEventsRouteState( null );
          }
          break;
        } case 3: {
          if ( null != state && state.getRoute() == $fu$_route_regionEvent ) {
            setRegionEventRouteState( state );
            routeStartIndex++;
            setRegionCode( state.getParameterValue( $fu$_regionCode_821487049 ) );
            setEventCode2( state.getParameterValue( $fu$_eventCode_1643987249 ) );
          } else {
            setRegionEventRouteState( null );
          }
          break;
        } default: {
          break;
        }
      }
    }
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
