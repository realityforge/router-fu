package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class BasicRouterRef
{
  @RouterRef
  BasicRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
