package com.example.route;

import elemental2.core.RegExp;
import elemental2.dom.Window;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.Elemental2HashBackend;
import router.fu.Location;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.Router;
import router.fu.Segment;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_RouteWithParameters extends RouteWithParameters implements RouteWithParametersService {
  private final Parameter $fu$_eventCode = new Parameter( "eventCode" );

  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Router $fu$_router;

  private final Route $fu$_route_regionEvent = new Route( "regionEvent", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode ), new Segment( "/events/" ), new Segment( $fu$_eventCode ), }, new Parameter[]{$fu$_regionCode, $fu$_eventCode, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvents = new Route( "regionEvents", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode ), new Segment( "/events" ) }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  RouterFu_RouteWithParameters(@Nonnull final Window window) {
    $fu$_router = new Router( this::onLocationChanged, new Elemental2HashBackend( window ), Collections.unmodifiableList( Arrays.asList( $fu$_route_regionEvent, $fu$_route_region, $fu$_route_regionEvents ) ) );
  }

  @Nonnull
  @Override
  public Route getRegionEventRoute() {
    return $fu$_route_regionEvent;
  }

  @Nonnull
  @Override
  public Route getRegionRoute() {
    return $fu$_route_region;
  }

  @Nonnull
  @Override
  public Route getRegionEventsRoute() {
    return $fu$_route_regionEvents;
  }

  @Nonnull
  @Override
  public String buildRegionEventLocation(@Nonnull final String regionCode, @Nonnull final String eventCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    $fu$_route_params.put( $fu$_eventCode, eventCode );
    return $fu$_route_regionEvent.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public String buildRegionLocation(@Nonnull final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    return $fu$_route_region.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public String buildRegionEventsLocation(@Nonnull final String regionCode) {
    final Map<Parameter, String> $fu$_route_params = new HashMap<>();
    $fu$_route_params.put( $fu$_regionCode, regionCode );
    return $fu$_route_regionEvents.buildLocation( $fu$_route_params );
  }

  @Nonnull
  @Override
  public void gotoRegionEvent(@Nonnull final String regionCode, @Nonnull final String eventCode) {
    $fu$_router.changeLocation( buildRegionEventLocation( regionCode, eventCode ) );
  }

  @Nonnull
  @Override
  public void gotoRegion(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildRegionLocation( regionCode ) );
  }

  @Nonnull
  @Override
  public void gotoRegionEvents(@Nonnull final String regionCode) {
    $fu$_router.changeLocation( buildRegionEventsLocation( regionCode ) );
  }

  void onLocationChanged(@Nonnull final Location location) {
  }
}
