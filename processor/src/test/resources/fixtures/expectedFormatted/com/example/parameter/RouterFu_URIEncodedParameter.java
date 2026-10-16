package com.example.parameter;

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
public class RouterFu_URIEncodedParameter extends URIEncodedParameter
    implements URIEncodedParameterService {
  private final Parameter $fu$_keyword_755992427 =
      new Parameter("keyword", "^((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$");

  private final Route $fu$_route_region = new Route(
      "region",
      new Segment[] {
        new Segment("/regions/"), new Segment($fu$_keyword_755992427),
      },
      new Parameter[] {
        $fu$_keyword_755992427,
      },
      "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$",
      (location, route, parameters) -> MatchResult.TERMINAL);

  private final Router $fu$_router;

  private @Nullable Location $fu$_location;

  private @Nullable RouteState $fu$_state_region;

  private @Nullable String $fu$_param_keyword;

  RouterFu_URIEncodedParameter(final Backend backend) {
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
  public Parameter getRegionKeywordParameter() {
    return $fu$_keyword_755992427;
  }

  @Override
  public @Nullable String getKeyword() {
    return $fu$_param_keyword;
  }

  void setKeyword(final @Nullable String value) {
    $fu$_param_keyword = value;
  }

  @Override
  public void updateKeyword(final String keyword) {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if (null != terminalState) {
      final Route route = terminalState.getRoute();
      if (route == $fu$_route_region) {
        gotoRegion(keyword);
      }
    }
  }

  @Override
  public String buildRegionLocation(final String keyword) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put($fu$_keyword_755992427, keyword);
    return $fu$_route_region.buildLocation($fu$_route_params);
  }

  @Override
  public void gotoRegion(final String keyword) {
    $fu$_router.changeLocation(buildRegionLocation(keyword));
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
    setKeyword(null);
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    {
      final RouteState state = states.size() > routeStartIndex ? states.get(routeStartIndex) : null;
      if (null != state && state.getRoute() == $fu$_route_region) {
        setRegionRouteState(state);
        routeStartIndex++;
        setKeyword(state.getParameterValue($fu$_keyword_755992427));
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
