package com.example.router_ref;

import javax.annotation.Nonnull;
import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
public class AnnotatedRouterRef
{
  @Nonnull
  @RouterRef
  AnnotatedRouterRefService getRouterService()
  {
    throw new IllegalStateException();
  }
}
