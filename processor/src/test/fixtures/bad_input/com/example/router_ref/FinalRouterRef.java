package com.example.router_ref;

import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
@org.jspecify.annotations.NullMarked
public class FinalRouterRef
{
  @RouterRef
  final FinalRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
