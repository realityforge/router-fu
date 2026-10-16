package com.example.callback;

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
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@NullMarked
@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_CallbackWithParams3 extends CallbackWithParams3
    implements CallbackWithParams3Service {
  private final Parameter $fu$_regionCode = new Parameter("regionCode");

  private final Route $fu$_route_region = new Route(
      "region",
      new Segment[] {
        new Segment("/regions/"), new Segment($fu$_regionCode),
      },
      new Parameter[] {
        $fu$_regionCode,
      },
      "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$",
      (String location, Route route, Map<Parameter, @Nullable String> parameters) ->
          regionCallback(parameters));

  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  private @Nullable RouteState $fu$_state_region;

  RouterFu_CallbackWithParams3(final Backend backend) {
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
  public Parameter getRegionRegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Override
  public String buildRegionLocation(final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put($fu$_regionCode, regionCode);
    return $fu$_route_region.buildLocation($fu$_route_params);
  }

  @Override
  public void gotoRegion(final String regionCode) {
    $fu$_router.changeLocation(buildRegionLocation(regionCode));
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
