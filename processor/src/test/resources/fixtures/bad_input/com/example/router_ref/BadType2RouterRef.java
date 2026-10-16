package com.example.router_ref;

import org.jspecify.annotations.NullMarked;
import router.fu.annotations.Router;
import router.fu.annotations.RouterRef;

@Router
@NullMarked
public class BadType2RouterRef
{
  @RouterRef
  String getRouterService()
  {
    throw new IllegalStateException();
  }
}
