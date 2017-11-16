package com.example.route;

import elemental2.core.RegExp;
import javax.annotation.Generated;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.Route;
import router.fu.Segment;

@Generated("router.fu.processor.RouterProcessor")
public class RouterFu_RouteWithParameters extends RouteWithParameters implements RouteWithParametersService {
  private final Parameter $fu$_eventCode = new Parameter( "eventCode" );

  private final Parameter $fu$_regionCode = new Parameter( "regionCode" );

  private final Route $fu$_route_regionEvent = new Route( "regionEvent", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode ), new Segment( "/events/" ), new Segment( $fu$_eventCode ), }, new Parameter[]{$fu$_regionCode, $fu$_eventCode, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_region = new Route( "region", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode ), }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );

  private final Route $fu$_route_regionEvents = new Route( "regionEvents", new Segment[]{new Segment( "regions/" ), new Segment( $fu$_regionCode ), new Segment( "/events" ) }, new Parameter[]{$fu$_regionCode, }, new RegExp( "^/regions/([a-zA-Z0-9\\-_]+)/events$" ), ( location, route, parameters ) -> MatchResult.TERMINAL );
}
