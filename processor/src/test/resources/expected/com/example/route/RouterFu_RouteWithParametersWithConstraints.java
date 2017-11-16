package com.example.route;

import elemental2.core.RegExp;
import javax.annotation.Generated;
import javax.annotation.Nonnull;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.Segment;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_RouteWithParametersWithConstraints extends RouteWithParametersWithConstraints implements RouteWithParametersWithConstraintsService {
  private final Parameter $fu$_eventCode_1643987249 = new Parameter( "eventCode", new RegExp( "[0-9]+" ) );

  private final Parameter $fu$_regionCode_821487049 = new Parameter( "regionCode", new RegExp( "[a-zA-Z]+" ) );

  private final Route $fu$_route_regionEvent = new Route( "regionEvent", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode_821487049 ), new Segment( "/events/" ), new Segment( $fu$_eventCode_1643987249 ), }, new Parameter[]{$fu$_regionCode_821487049, $fu$_eventCode_1643987249, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode_821487049 ), }, new Parameter[]{$fu$_regionCode_821487049, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvents = new Route( "regionEvents", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode_821487049 ), new Segment( "/events" ) }, new Parameter[]{$fu$_regionCode_821487049, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  @Nonnull
  @Override
  public Route getegionEventRoute() {
    return $fu$_route_regionEvent;
  }

  @Nonnull
  @Override
  public Route getegionRoute() {
    return $fu$_route_region;
  }

  @Nonnull
  @Override
  public Route getegionEventsRoute() {
    return $fu$_route_regionEvents;
  }
}
