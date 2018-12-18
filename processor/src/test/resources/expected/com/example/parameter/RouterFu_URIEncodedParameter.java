package com.example.parameter;

import elemental2.core.JsRegExp;
import elemental2.dom.Window;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import router.fu.HashBackend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.RouteState;
import router.fu.Router;
import router.fu.Segment;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_URIEncodedParameter extends URIEncodedParameter implements URIEncodedParameterService {
  private final Parameter $fu$_keyword_755992427 = new Parameter( "keyword", new JsRegExp( "((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)" ) );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "/regions/" ), new Segment( $fu$_keyword_755992427 ), }, new Parameter[]{$fu$_keyword_755992427, }, new JsRegExp( "^/regions/((?:[a-zA-Z0-9\\-_\\$\\.\\+!\\*'\\(\\)\\,\\\"~]|%[A-F0-9]{2})+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Router $fu$_router;

  private Location $fu$_location;

  private RouteState $fu$_state_region;

  private String $fu$_param_keyword;

  RouterFu_URIEncodedParameter(@Nonnull final Window window) {
    $fu$_router = new Router( this::onLocationChanged, new HashBackend( window ), Collections.unmodifiableList( Arrays.asList( $fu$_route_region ) ) );
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
  public Parameter getRegionKeywordParameter() {
    return $fu$_keyword_755992427;
  }

  @Nullable
  @Override
  public String getKeyword() {
    return $fu$_param_keyword;
  }

  void setKeyword(@Nullable final String value) {
    $fu$_param_keyword = value;
  }

  @Override
  public void updateKeyword(@Nonnull final String keyword) {
    final Location location = getLocation();
    final RouteState terminalState = location.getTerminalState();
    if ( null != terminalState ) {
      final Route route = terminalState.getRoute();
      if ( route == $fu$_route_region ) {
        gotoRegion(keyword);
      }
    }
  }

  @Nonnull
  @Override
  public String buildRegionLocation(@Nonnull final String keyword) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_keyword_755992427, keyword );
    return $fu$_route_region.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public void gotoRegion(@Nonnull final String keyword) {
    $fu$_router.changeLocation( buildRegionLocation( keyword ) );
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
    setKeyword( null );
    final List<RouteState> states = location.getStates();
    int routeStartIndex = 0;
    for ( int i = 0; i < 1; i++ ) {
      final RouteState state = states.size() > routeStartIndex ? states.get( routeStartIndex ) : null;
      switch ( i ) {
        case 0: {
          if ( null != state && state.getRoute() == $fu$_route_region ) {
            setRegionRouteState( state );
            routeStartIndex++;
            setKeyword( state.getParameterValue( $fu$_keyword_755992427 ) );
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
  public final void reRoute() {
    $fu$_router.reRoute();
  }
}
