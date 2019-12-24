package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class ParametersRouterRef
{
  @RouterRef
  ParametersRouterRefService getRouterService( int i )
  {
    throw new IllegalStateException();
  }
}
