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
public class RouterFu_RouteWithLongStaticSegment extends RouteWithLongStaticSegment
    implements RouteWithLongStaticSegmentService {
  private final Route $fu$_route_region = new Route(
      "region",
      new Segment[] {
        new Segment("/regions/ballarat/events/23/coordination/now/shift1/crew23/position2")
      },
      new Parameter[] {},
      "^/regions/ballarat/events/23/coordination/now/shift1/crew23/position2$",
      (location, route, parameters) -> MatchResult.TERMINAL);

  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  private @Nullable RouteState $fu$_state_region;

  RouterFu_RouteWithLongStaticSegment(final Backend backend) {
    $fu$_router = new Router(
        this::onLocationChanged,
        backend,
        Collections.unmodifiableList(Arrays.asList($fu$_route_region)));
    $fu$_router.activate();
  }

  @Override
  public Route getRegionRoute() {
    return $fu$_route_region;
  }

  @Override
  public @Nullable RouteState getRegionRouteState() {
    return $fu$_state_region;
  }

  void setRegionRouteState(final @Nullable RouteState state) {
    $fu$_state_region = state;
  }

  @Override
  public String buildRegionLocation() {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    return $fu$_route_region.buildLocation($fu$_route_params);
  }

  @Override
  public void gotoRegion() {
    $fu$_router.changeLocation(buildRegionLocation());
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
      if (null != state && state.getRoute() == $fu$_route_region) {
        setRegionRouteState(state);
        routeStartIndex++;
      } else {
        setRegionRouteState(null);
      }
    }
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
