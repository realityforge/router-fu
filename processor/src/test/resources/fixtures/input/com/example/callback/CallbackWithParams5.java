package com.example.callback;

import java.util.Map;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import router.fu.MatchResult;
import router.fu.Parameter;
import router.fu.annotations.Route;
import router.fu.annotations.RouteCallback;
import router.fu.annotations.Router;

@Router
@Route( name = "region", path = "regions/:regionCode" )
@NullMarked
public class CallbackWithParams5
{
  @RouteCallback
  MatchResult regionCallback( router.fu.Route route, Map<Parameter, @Nullable String> parameters, String location )
  {
    return MatchResult.TERMINAL;
  }
}
