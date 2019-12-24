package com.example.callback;

import router.fu.MatchResult;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
public class DuplicateCallback
{
  @RouteCallback
  MatchResult regionCallback()
  {
    return MatchResult.TERMINAL;
  }

  @RouteCallback( name = "region" )
  MatchResult regionCallback2()
  {
    return MatchResult.TERMINAL;
  }
}
