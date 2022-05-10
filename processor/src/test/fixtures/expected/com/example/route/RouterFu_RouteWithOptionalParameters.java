package com.example.route;

import akasha.core.RegExp;
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

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_RouteWithOptionalParameters extends RouteWithOptionalParameters implements RouteWithOptionalParametersService {
  private final Parameter $fu$_filter = new Parameter( "filter" );

  private final Parameter $fu$_eventId = new Parameter( "eventId" );

  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})*)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvents = new Route( "regionEvents", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), new Segment( "/events/" ), new Segment( $fu$_filter ), }, new Parameter[]{$fu$_regionCode, $fu$_filter, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})*)/events/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})*)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvent = new Route( "regionEvent", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), new Segment( "/event/" ), new Segment( $fu$_eventId ), new Segment( "/" ), new Segment( $fu$_filter ), }, new Parameter[]{$fu$_regionCode, $fu$_eventId, $fu$_filter, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})*)/event/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})*)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_region;

  private RouteState $fu$_state_regionEvents;

  private RouteState $fu$_state_regionEvent;

  RouterFu_RouteWithOptionalParameters(@Nonnull final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList( $fu$_route_region, $fu$_route_regionEvents, $fu$_route_regionEvent ) ) );
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
  public Parameter getRegionRegionCodeParameter() {
    return $fu$_regionCode;
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
  public Parameter getRegionEventsRegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Nonnull
  @Override
  public Parameter getRegionEventsFilterParameter() {
    return $fu$_filter;
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

  @Nonnull
  @Override
  public Parameter getRegionEventRegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Nonnull
  @Override
  public Parameter getRegionEventEventIdParameter() {
    return $fu$_eventId;
  }

  @Nonnull
  @Override
  public Parameter getRegionEventFilterParameter() {
    return $fu$_filter;
  }

  @Nonnull
  @Override
  public String buildRegionLocation(@Nonnull final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    return $fu$_route_region.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoRegion(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildRegionLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventsLocation(@Nonnull final String regionCode,
      @Nonnull final String filter) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    $fu$_route_params.put( $fu$_filter, filter );
    return $fu$_route_regionEvents.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoRegionEvents(@Nonnull final String regionCode, @Nonnull final String filter) {
    $fu$_router.changeLocation( buildRegionEventsLocation( regionCode, filter ) );
  }

  @Nonnull
  @Override
  public String buildRegionEventLocation(@Nonnull final String regionCode,
      @Nonnull final String eventId, @Nonnull final String filter) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    $fu$_route_params.put( $fu$_eventId, eventId );
    $fu$_route_params.put( $fu$_filter, filter );
    return $fu$_route_regionEvent.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoRegionEvent(@Nonnull final String regionCode, @Nonnull final String eventId,
      @Nonnull final String filter) {
    $fu$_router.changeLocation( buildRegionEventLocation( regionCode, eventId, filter ) );
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
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 3; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        case 0: {
          if ( null != state && state.getRoute() == $fu$_route_region ) {
            setRegionRouteState( state );
            routeStartIndex++;
          } else {
            setRegionRouteState( null );
          }
          break;
        } case 1: {
          if ( null != state && state.getRoute() == $fu$_route_regionEvents ) {
            setRegionEventsRouteState( state );
            routeStartIndex++;
          } else {
            setRegionEventsRouteState( null );
          }
          break;
        } case 2: {
          if ( null != state && state.getRoute() == $fu$_route_regionEvent ) {
            setRegionEventRouteState( state );
            routeStartIndex++;
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
