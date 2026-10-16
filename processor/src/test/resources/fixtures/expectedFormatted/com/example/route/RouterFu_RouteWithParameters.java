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
public class RouterFu_RouteWithParameters extends RouteWithParameters
    implements RouteWithParametersService {
  private final Parameter $fu$_eventCode = new Parameter("eventCode");

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
      (location, route, parameters) -> MatchResult.TERMINAL);

  private final Route $fu$_route_regionEvents = new Route(
      "regionEvents",
      new Segment[] {new Segment("/regions/"), new Segment($fu$_regionCode), new Segment("/events")
      },
      new Parameter[] {
        $fu$_regionCode,
      },
      "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)/events$",
      (location, route, parameters) -> MatchResult.TERMINAL);

  private final Route $fu$_route_regionEvent = new Route(
      "regionEvent",
      new Segment[] {
        new Segment("/regions/"),
        new Segment($fu$_regionCode),
        new Segment("/events/"),
        new Segment($fu$_eventCode),
      },
      new Parameter[] {
        $fu$_regionCode, $fu$_eventCode,
      },
      "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)/events/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$",
      (location, route, parameters) -> MatchResult.TERMINAL);

  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  private @Nullable RouteState $fu$_state_region;

  private @Nullable RouteState $fu$_state_regionEvents;

  private @Nullable RouteState $fu$_state_regionEvent;

  RouterFu_RouteWithParameters(final Backend backend) {
    $fu$_router = new Router(
        this::onLocationChanged,
        backend,
        Collections.unmodifiableList(
            Arrays.asList($fu$_route_region, $fu$_route_regionEvents, $fu$_route_regionEvent)));
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
  public Route getRegionEventsRoute() {
    return $fu$_route_regionEvents;
  }

  @Override
  public @Nullable RouteState getRegionEventsRouteState() {
    return $fu$_state_regionEvents;
  }

  void setRegionEventsRouteState(final @Nullable RouteState state) {
    $fu$_state_regionEvents = state;
  }

  @Override
  public Parameter getRegionEventsRegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Override
  public Route getRegionEventRoute() {
    return $fu$_route_regionEvent;
  }

  @Override
  public @Nullable RouteState getRegionEventRouteState() {
    return $fu$_state_regionEvent;
  }

  void setRegionEventRouteState(final @Nullable RouteState state) {
    $fu$_state_regionEvent = state;
  }

  @Override
  public Parameter getRegionEventRegionCodeParameter() {
    return $fu$_regionCode;
  }

  @Override
  public Parameter getRegionEventEventCodeParameter() {
    return $fu$_eventCode;
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
  public String buildRegionEventsLocation(final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put($fu$_regionCode, regionCode);
    return $fu$_route_regionEvents.buildLocation($fu$_route_params);
  }

  @Override
  public void gotoRegionEvents(final String regionCode) {
    $fu$_router.changeLocation(buildRegionEventsLocation(regionCode));
  }

  @Override
  public String buildRegionEventLocation(final String regionCode, final String eventCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put($fu$_regionCode, regionCode);
    $fu$_route_params.put($fu$_eventCode, eventCode);
    return $fu$_route_regionEvent.buildLocation($fu$_route_params);
  }

  @Override
  public void gotoRegionEvent(final String regionCode, final String eventCode) {
    $fu$_router.changeLocation(buildRegionEventLocation(regionCode, eventCode));
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
    {
      final RouteState state = states.size() > routeStartIndex ? states.get(routeStartIndex) : null;
      if (null != state && state.getRoute() == $fu$_route_regionEvents) {
        setRegionEventsRouteState(state);
        routeStartIndex++;
      } else {
        setRegionEventsRouteState(null);
      }
    }
    {
      final RouteState state = states.size() > routeStartIndex ? states.get(routeStartIndex) : null;
      if (null != state && state.getRoute() == $fu$_route_regionEvent) {
        setRegionEventRouteState(state);
        routeStartIndex++;
      } else {
        setRegionEventRouteState(null);
      }
    }
  }

  @Override
  public void reRoute() {
    $fu$_router.reRoute();
  }
}
