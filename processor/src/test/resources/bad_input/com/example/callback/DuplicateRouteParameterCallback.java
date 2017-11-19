package com.example.callback;

import router.fu.MatchResult;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
public class DuplicateRouteParameterCallback
{
  @RouteCallback
  MatchResult regionCallback( router.fu.Route route, router.fu.Route route2 )
  {
    return MatchResult.TERMINAL;
  }
}
