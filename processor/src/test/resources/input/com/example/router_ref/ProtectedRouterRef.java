package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class ProtectedRouterRef
{
  @RouterRef
  protected ProtectedRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
