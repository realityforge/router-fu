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
public class RouterFu_RouteWithEmptyPath extends RouteWithEmptyPath
    implements RouteWithEmptyPathService {
  private final Route $fu$_route_welcome = new Route(
      "welcome",
      new Segment[] {},
      new Parameter[] {},
      "^$",
      (location, route, parameters) -> MatchResult.TERMINAL);

  private final Route $fu$_route_filter = new Route(
      "filter",
      new Segment[] {},
      new Parameter[] {},
      "^",
      (location, route, parameters) -> MatchResult.NON_TERMINAL);

  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  private @Nullable RouteState $fu$_state_welcome;

  private @Nullable RouteState $fu$_state_filter;

  RouterFu_RouteWithEmptyPath(final Backend backend) {
    $fu$_router = new Router(
        this::onLocationChanged,
        backend,
        Collections.unmodifiableList(Arrays.asList($fu$_route_welcome, $fu$_route_filter)));
    $fu$_router.activate();
  }

  @Override
  public Route getWelcomeRoute() {
    return $fu$_route_welcome;
  }

  @Override
  public @Nullable RouteState getWelcomeRouteState() {
    return $fu$_state_welcome;
  }

  void setWelcomeRouteState(final @Nullable RouteState state) {
    $fu$_state_welcome = state;
  }

  @Override
  public Route getFilterRoute() {
    return $fu$_route_filter;
  }

  @Override
  public @Nullable RouteState getFilterRouteState() {
    return $fu$_state_filter;
  }

  void setFilterRouteState(final @Nullable RouteState state) {
    $fu$_state_filter = state;
  }

  @Override
  public String buildWelcomeLocation() {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    return $fu$_route_welcome.buildLocation($fu$_route_params);
  }

  @Override
  public void gotoWelcome() {
    $fu$_router.changeLocation(buildWelcomeLocation());
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
    setLocation(location);
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    {
      final RouteState state = states.size() > routeStartIndex ? states.get(routeStartIndex) : null;
      if (null != state && state.getRoute() == $fu$_route_welcome) {
        setWelcomeRouteState(state);
        routeStartIndex++;
      } else {
        setWelcomeRouteState(null);
      }
    }
    {
      final RouteState state = states.size() > routeStartIndex ? states.get(routeStartIndex) : null;
      if (null != state && state.getRoute() == $fu$_route_filter) {
        setFilterRouteState(state);
        routeStartIndex++;
      } else {
        setFilterRouteState(null);
      }
    }
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
