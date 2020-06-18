package com.example.route;

import elemental2.core.JsRegExp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.Backend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_RouteWithNonTargetParameters extends RouteWithNonTargetParameters implements RouteWithNonTargetParametersService {
  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Route $fu$_route_regionFilter = new Route( "regionFilter", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new JsRegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> MatchResult.NON_TERMINAL );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new JsRegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_regionFilter;

  private RouteState $fu$_state_region;

  RouterFu_RouteWithNonTargetParameters(@Nonnull final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList( $fu$_route_regionFilter, $fu$_route_region ) ) );
    $fu$_router.activate();
  }

  @Nonnull
  @Override
  public Route getRegionFilterRoute() {
    return $fu$_route_regionFilter;
  }

  @Nullable
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
    for ( int i = 0; i < 2; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        case 0: {
          if ( null != state && state.getRoute() == $fu$_route_regionFilter ) {
            setRegionFilterRouteState( state );
            routeStartIndex++;
          } else {
            setRegionFilterRouteState( null );
          }
          break;
        } case 1: {
          if ( null != state && state.getRoute() == $fu$_route_region ) {
            setRegionRouteState( state );
            routeStartIndex++;
          } else {
            setRegionRouteState( null );
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
