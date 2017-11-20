package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class BadTypeRouterRef
{
  @RouterRef
  int getRouterService()
  {
    throw new IllegalStateException();
  }
}
