package com.example.route;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.Backend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_RouteParametersWithShortName extends RouteParametersWithShortName implements RouteParametersWithShortNameService {
  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Route $fu$_route_A = new Route( "A", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$", ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  private @Nullable RouteState $fu$_state_A;

  RouterFu_RouteParametersWithShortName(final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList( $fu$_route_A ) ) );
    $fu$_router.activate();
  }

  @Override
  public Route getARoute() {
    return $fu$_route_A;
  }

  @Override
  public @Nullable RouteState getARouteState() {
    return $fu$_state_A;
  }

  void setARouteState(final @Nullable RouteState state) {
    $fu$_state_A = state;
  }

  @Override
  public Parameter getARegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Override
  public String buildALocation(final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    return $fu$_route_A.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoA(final String regionCode) {
    $fu$_router.changeLocation( buildALocation( regionCode ) );
  }

  @Override
  public Location getLocation() {
    assert null != $fu$_location;
    return $fu$_location;
  }

  void setLocation(final Location location) {
    $fu$_location = location;
  }

  void onLocationChanged(final Location location) {
    setLocation( location );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
     {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      if ( null != state && state.getRoute() == $fu$_route_A ) {
        setARouteState( state );
        routeStartIndex++;
      } else {
        setARouteState( null );
      }
    }
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
