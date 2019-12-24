package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class StaticRouterRef
{
  @RouterRef
  static StaticRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
