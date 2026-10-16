package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
@org.jspecify.annotations.NullMarked
public class BadTypeRouterRef
{
  @RouterRef
  int getRouterService()
  {
    throw new IllegalStateException();
  }
}
