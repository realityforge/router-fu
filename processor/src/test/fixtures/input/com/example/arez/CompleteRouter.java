package com.example.arez;

import router.fu.MatchResult;
import router.fu.annotations.BoundParameter;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router( arez = true )
@Route( name = "regionFilter", path = "regions/:regionCode", navigationTarget = false )
@Route( name = "region", path = "regions/:regionCode<[a-zA-Z]+>" )
@Route( name = "regionEvents", path = "regions/:regionCode<[a-zA-Z]+>/events" )
@Route( name = "regionEvent", path = "regions/:regionCode<[a-zA-Z]+>/events/:eventCode<[0-9]+>" )
@BoundParameter( name = "regionCode" )
@BoundParameter( name = "eventCode", routeNames = { "regionEvent" } )
@BoundParameter( name = "eventCode2", parameterName = "eventCode" )
public class CompleteRouter
{
  @RouteCallback
  MatchResult regionFilterCallback()
  {
    return MatchResult.TERMINAL;
  }
}
