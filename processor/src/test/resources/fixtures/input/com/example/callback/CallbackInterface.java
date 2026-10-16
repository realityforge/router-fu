package com.example.callback;

import org.jspecify.annotations.NullMarked;
import router.fu.MatchResult;
import router.fu.annotations.RouteCallback;

@NullMarked
public interface CallbackInterface
{
  @RouteCallback
  default MatchResult regionCallback()
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
