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
public class RouterFu_RouteWithEmptyPath extends RouteWithEmptyPath implements RouteWithEmptyPathService {
  private final Route $fu$_route_welcome = new Route( "welcome", new Segment[]{}, new Parameter[]{}, new RegExp( "^$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_welcome;

  RouterFu_RouteWithEmptyPath(@Nonnull final Backend backend) {
    $fu$_router = new Router( this::onLocationChanged, backend, Collections.unmodifiableList( Arrays.asList( $fu$_route_welcome ) ) );
    $fu$_router.activate();
  }

  @Nonnull
  @Override
  public Route getWelcomeRoute() {
    return $fu$_route_welcome;
  }

  @Nullable
  @Override
  public RouteState getWelcomeRouteState() {
    return $fu$_state_welcome;
  }

  void setWelcomeRouteState(@Nullable final RouteState state) {
    $fu$_state_welcome = state;
  }

  @Nonnull
  @Override
  public String buildWelcomeLocation() {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    return $fu$_route_welcome.buildLocation( $fu$_route_params );
  }

  @Override
  public void gotoWelcome() {
    $fu$_router.changeLocation( buildWelcomeLocation(  ) );
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
          if ( null != state && state.getRoute() == $fu$_route_welcome ) {
            setWelcomeRouteState( state );
            routeStartIndex++;
          } else {
            setWelcomeRouteState( null );
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
