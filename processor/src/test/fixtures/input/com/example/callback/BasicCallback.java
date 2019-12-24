package com.example.callback;

import router.fu.MatchResult;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
public class BasicCallback
{
  @RouteCallback
  MatchResult regionCallback()
  {
    final boolean authenticated = true;
    //noinspection ConstantConditions
    if ( authenticated )
    {
      return MatchResult.NON_TERMINAL;
    }
    else
    {
      return MatchResult.TERMINAL;
    }
  }
}
