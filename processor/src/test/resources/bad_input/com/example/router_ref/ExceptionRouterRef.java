package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class ExceptionRouterRef
{
  @RouterRef
  ExceptionRouterRefService getRouterService()
    throws Exception
  {
    throw new IllegalStateException();
  }
}
