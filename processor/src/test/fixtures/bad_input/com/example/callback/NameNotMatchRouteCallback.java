package com.example.callback;

import router.fu.MatchResult;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "notRegion", path = "regions/:regionCode" )
public class NameNotMatchRouteCallback
{
  @RouteCallback
  MatchResult regionCallback()
  {
    return MatchResult.TERMINAL;
  }
}
