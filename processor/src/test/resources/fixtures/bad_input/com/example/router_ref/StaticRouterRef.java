package com.example.router_ref;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
@NullMarked
public class StaticRouterRef
{
  @RouterRef
  static StaticRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
