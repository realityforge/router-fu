package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class MultiRouterRef
{
  @RouterRef
  MultiRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }

  @RouterRef
  MultiRouterRefService getRouterService2()
  {
    throw new IllegalStateException();
  }
}
