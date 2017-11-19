package com.example.callback;

import java.util.Map;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
public class CallbackWithParams5
{
  @RouteCallback
  MatchResult regionCallback( router.fu.Route route, Map<Parameter, String> parameters, String location )
  {
    return MatchResult.TERMINAL;
  }
}
