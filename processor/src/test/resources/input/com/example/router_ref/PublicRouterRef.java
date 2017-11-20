package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class PublicRouterRef
{
  @RouterRef
  public PublicRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
