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
public class RouterFu_RouteParametersWithShortName extends RouteParametersWithShortName implements RouteParametersWithShortNameService {
  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Route $fu$_route_A = new Route( "A", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_A;

  RouterFu_RouteParametersWithShortName(@Nonnull final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList( $fu$_route_A ) ) );
    $fu$_router.activate();
  }

  @Nonnull
  @Override
  public Route getARoute() {
    return $fu$_route_A;
  }

  @Nullable
  @Override
  public RouteState getARouteState() {
    return $fu$_state_A;
  }

  void setARouteState(@Nullable final RouteState state) {
    $fu$_state_A = state;
  }

  @Nonnull
  @Override
  public Parameter getARegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Nonnull
  @Override
  public String buildALocation(@Nonnull final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    return $fu$_route_A.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoA(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildALocation( regionCode ) );
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
    for ( int i = 0; i < 1; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        case 0: {
          if ( null != state && state.getRoute() == $fu$_route_A ) {
            setARouteState( state );
            routeStartIndex++;
          } else {
            setARouteState( null );
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
