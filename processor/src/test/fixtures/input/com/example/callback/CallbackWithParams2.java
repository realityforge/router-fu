package com.example.callback;

import org.jspecify.annotations.NullMarked;
import router.fu.MatchResult;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@NullMarked
public class CallbackWithParams2
{
  @RouteCallback
  MatchResult regionCallback( router.fu.Route location )
  {
    return MatchResult.TERMINAL;
  }
}
