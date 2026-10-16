package com.example.callback;

import java.util.Map;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@org.jspecify.annotations.NullMarked
public class CallbackWithParams6
{
  @RouteCallback
  MatchResult regionCallback( Map<Parameter, @org.jspecify.annotations.Nullable String> parameters, router.fu.Route route, String location )
  {
    return MatchResult.TERMINAL;
  }
}
